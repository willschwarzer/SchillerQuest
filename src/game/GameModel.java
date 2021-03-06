package game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameModel implements Subject {
	private GameMap currentMap;
	private List<GameMap> maps;
	private Controller controller;
	private List<Observer> observers;
	private MapGenerator generator;

	/**
	 * Generates a new GameModel without a premade level (generates all levels
	 * procedurally). Currently the only used constructor.
	 *
	 * @param controller the object to be used as the GameModel's controller
	 */
	public GameModel(Controller controller) {
		observers = new ArrayList<>();
		this.controller = controller;
		addObserver(controller);

		maps = new ArrayList<>();
		generator = new MapGenerator();
		System.out.println("Seed: " + generator.getInitialRandomSeed() + "L"); // L necessary at the end of the long
		currentMap = generator.generate(1);
		maps.add(currentMap);
		spawnPlayer();

		notifyObservers();
	}

	/**
	 * Creates a GameModel with a premade map.
	 * (Not currently used.)
	 *
	 * @param controller the object to be the model's controller
	 * @param map the object to be the model's map
	 * @param startCoordinates the player's starting coordinates
	 */
	public GameModel(Controller controller, GameMap map, Coordinates startCoordinates) {
		observers = new ArrayList<>();
		this.controller = controller;
		addObserver(controller);

		this.currentMap = map;
		//spawnPlayer(startCoordinates);

		notifyObservers();
	}

	/**
	 * Creates a new level from a premade text file
	 * (Not currently used)
	 *
	 * @param fileName the name of the text file containing the level to be loaded
	 * @param startCoordinates the starting coordinates of the player
	 */
	private void loadNewLevel(String fileName, Coordinates startCoordinates) {
		currentMap = new GameMap(new File(fileName));
		//spawnPlayer(startCoordinates);
	}

	/**
	 * Moves a given creatures location with given move
	 *
	 * @param creature The creature being moved
	 * @param move     The changes in the creatures location
	 */
	public void moveCreature(Creature creature, int[] move) {
		Coordinates currentCoordinates = creature.getCoordinates();
		Tile oldTile = currentMap.getTileAtLocation(currentCoordinates);
		int x = currentCoordinates.getX();
		int y = currentCoordinates.getY();
		x += move[0];
		y += move[1];
		Coordinates destinationCoordinates = new Coordinates(x, y);
		Tile newTile = currentMap.getTileAtLocation(destinationCoordinates);

		if (newTile.getCreature() != null) {
			attack(creature, newTile.getCreature());
			if (newTile.getCreature().getStats().getHealth() <= 0) {
				creatureDeath(newTile.getCreature(), newTile, creature);
			}
		} else if (newTile.isOccupiableTerrain()) {
			oldTile.removeEntity(creature);
			newTile.addEntity(creature);
			creature.setCoordinates(destinationCoordinates);
		} else {
			return;
		}
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setGameMap(GameMap map) {
		this.currentMap = map;
	}

	public Player getPlayer() {
		return currentMap.getPlayer();
	}

	public void attack(Creature attacker, Creature attackee) {
		int attack = attacker.getStats().getAttack();
		if (attacker.getEquipped().containsKey("weapon")) {
			Item attackerWeapon = attacker.getEquipped().get("weapon");
			attack += attackerWeapon.getBoost();
		}

		int defense = attackee.getStats().getDefense();
		if (attackee.getEquipped().containsKey("armor")) {
			Item attackeeArmor = attackee.getEquipped().get("armor");
			defense += attackeeArmor.getBoost();
		}

		if (attackee.getEquipped().containsKey("shield")) {
			Item shield = attackee.getEquipped().get("shield");
			defense = +shield.getBoost();
		}

		int attackeeSpd = attackee.getStats().getSpeed();
		int attackerSpd = attacker.getStats().getSpeed();

		if (attackee.getEquipped().containsKey("shoes")) {
			Item attackeeShoes = attackee.getEquipped().get("shoes");
			attackeeSpd += attackeeShoes.getBoost();
		}
		if (attacker.getEquipped().containsKey("shoes")) {
			Item attackerShoes = attacker.getEquipped().get("shoes");
			attackeeSpd += attackerShoes.getBoost();
		}
		String eventString = "";

		int hitChance = randomWithRange(attackerSpd / 2, attackerSpd) - randomWithRange(attackeeSpd / 4, attackeeSpd);

		int damage = 0;
		if (hitChance > 0) {
			damage += randomWithRange(attack / 2, attack) - randomWithRange(defense / 3, defense);
		} else {
			eventString = attacker.getName() + "'s attack missed";
		}

		if (damage > 0) {
			attackee.getStats().setHealth(attackee.getStats().getHealth() - damage);
			eventString = attacker.getName() + "'s attack did " + damage + " point(s) of damage to " + attackee
					.getName();
		} else if (hitChance > 0) {
			eventString = attacker.getName() + "'s attack did no damage to " + attackee.getName();
		}
		controller.log(eventString);
	}

	/**
	 * Will allow all of the other active entities to take a turn
	 */
	public void takeTurn() {
		List<Monster> monsters = currentMap.getMonsters();

		for (int i = 0; i < monsters.size(); i++) {
			moveCreature(monsters.get(i), monsters.get(i).getMove());
		}
		notifyObservers();
	}

	/**
	 * Creates a Player at given coordinate location
	 */
	public void spawnPlayer() {
		Stats playerStats = new Stats(100, 5, 5, 5, 4);
		// Spawns player with null coordinates, to be immediately overwritten
		Player player = new Player(null, playerStats);
		currentMap.setPlayer(player);
	}

	@Override
	public boolean addObserver(Observer observer) {
		return observers.add(observer);
	}

	@Override
	public boolean removeObserver(Observer o) {
		return observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(
					currentMap.getVisionAsCharArray(getPlayer().getCoordinates(), getPlayer().getStats().getVision()));
		}
	}

	private int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		int value = (int) (Math.random() * range) + min;
		return value;
	}

	private void creatureDeath(Creature dead, Tile tile, Creature attacker) {
		String deathMessage = dead.getName() + " died";
		controller.log(deathMessage);

		if (dead == getPlayer()) {
			String gameOver = dead.getName() + " died";
			dead.getStats().setVision(100);
			controller.log(deathMessage);
		} else {
			if (Monster.class.isAssignableFrom(dead.getClass())) {
				currentMap.removeMonster((Monster) dead);
				tile.removeEntity(dead);
				if(attacker == getPlayer()) {
					if (getPlayer().gainExp(dead.getStats().getLevel())) {
						String levelUp = "Player level up! you are now level " + getPlayer().getStats().getLevel();
						controller.log(levelUp);
					}
				}
			} else {
				throw new IllegalArgumentException(
						"Currently cannot have a creature that isn't a Player or a Monster die.");
			}
		}
	}

	/**
	 * If the player is on a down staircase, moves to the next level
	 * (generating it if it is not already present). Otherwise, displays an
	 * appropriate message and does nothing.
	 */
	public void useDownStaircase() {
		Player player = getPlayer();
		Tile playerTile = currentMap.getTileAtLocation(player.getCoordinates());
		if (playerTile.hasDownStaircase()) {
			int newIndex = maps.indexOf(currentMap) + 1;
			if (newIndex == maps.size()) {
				// Next level not already present; we need to generate it
				GameMap newMap = generator.generate(newIndex + 1);
				newMap.setPlayer(player);
				maps.add(newMap);
			}
			currentMap = maps.get(newIndex);
			currentMap.placePlayerAtUpStaircase();
			player.setGameMap(currentMap);
			playerTile.removeEntity(player);
			notifyObservers();
		} else {
			controller.log("You can only go down a down staircase.");
		}
	}

	/**
	 * If the player is on an up staircase, moves to the previous level. Otherwise,
	 * displays an appropriate message and does nothing.
	 */
	public void useUpStaircase() {
		Player player = getPlayer();
		Tile playerTile = currentMap.getTileAtLocation(player.getCoordinates());
		if (playerTile.hasUpStaircase()) {
			int newIndex = maps.indexOf(currentMap) - 1;
			if (newIndex < 0) {
				controller.openTitleScreen();
			} else {
				playerTile.removeEntity(player);
				currentMap = maps.get(newIndex);
				currentMap.placePlayerAtDownStaircase();
				player.setGameMap(currentMap);
				notifyObservers();
			}
		} else {
			controller.log("You can only go up an up staircase.");
		}
	}

	public void pickUp() {
		Player player = getPlayer();
		Coordinates playerCoordinates = player.getCoordinates();
		Tile tile = currentMap.getTileAtLocation(playerCoordinates);
		List<Item> backpack = player.getBackpack();
		if (!tile.getItems().isEmpty()) {
			if (backpack.size() < Player.MAX_BACKPACK_SIZE) {
				Item item = tile.getItems().pop();
				tile.removeEntity(item);
				item.setCoordinates(null);
				player.getBackpack().add(item);
				controller.log("You pick up the " + item.getName());
			} else {
				controller.log("Your backpack is too full.");
			}
		} else {
			controller.log("There is nothing here to pick up.");
		}
	}

	public void drop(Item item) {
		Player player = getPlayer();
		Coordinates playerCoordinates = player.getCoordinates();
		player.getBackpack().remove(item);
		Tile tile = currentMap.getTileAtLocation(playerCoordinates);
		tile.addEntity(item);
		item.setCoordinates(playerCoordinates);
		controller.log("You drop the " + item.getName());
	}
}
