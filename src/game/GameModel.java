package game;

import game.monsters.*;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class GameModel implements Subject {
	private GameMap currentMap;
	private List<GameMap> maps;
	private Controller controller;
	private List<Observer> observers;
	private MapGenerator generator;

	public GameModel(Controller controller) {
		observers = new ArrayList<>();
		this.controller = controller;
		addObserver(controller);

		maps = new ArrayList();
		generator = new MapGenerator();
		System.out.println("Seed: " + generator.getInitialRandomSeed() + "L"); // L necessary at the end of the long
		currentMap = generator.generate(1);
		maps.add(currentMap);
		spawnPlayer();

		notifyObservers();
	}

	public GameModel(Controller controller, GameMap map, Coordinates startCoordinates) {
		observers = new ArrayList<>();
		this.controller = controller;
		addObserver(controller);

		this.currentMap = map;
		//spawnPlayer(startCoordinates);

		notifyObservers();
	}

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
				creatureDeath(newTile.getCreature(), newTile);
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
			eventString = "'s attack did no damage to " + attackee.getName();
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
		//TODO: Fix hard coding in the beginning stats
		Stats playerStats = new Stats(100, 10, 10, 10, 4);
		// Spawns player with null coordinates, to be immediately overwritten
		Player player = new Player(null, playerStats);
		currentMap.setPlayer(player);
	}

	/**
	 * Creates a Monster at given coordinate location.
	 * This will be replaced with automatic generation of monster on each level
	 *
	 * @param coordinates The coordinate the player will be spawned at
	 */
	public void spawnMonster(Coordinates coordinates, int level) {
		Rat monster = new Rat(coordinates, currentMap, level);
		currentMap.setMonster(monster);
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
			observer.update(currentMap.getVisionAsCharArray(getPlayer().getCoordinates(), getPlayer().getStats().getVision
					()));
		}
	}

	private int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		int value = (int) (Math.random() * range) + min;
		return value;
	}

	private void creatureDeath(Creature dead, Tile newTile) {
		if (dead == getPlayer()) {
			System.out.println("Game Over");
			//GameOver
		} else {
			if (Monster.class.isAssignableFrom(dead.getClass())) {
				currentMap.removeMonster((Monster) dead);
				newTile.removeEntity(dead);
			} else {
				throw new IllegalArgumentException(
						"Currently cannot have a Creature that is not a Player or Monster die.");
			}
		}
	}

	public void useDownStaircase() {
		Player player = getPlayer();
		Tile playerTile = currentMap.getTileAtLocation(player.getCoordinates());
		if (playerTile.hasDownStaircase()) {
			int newIndex = maps.indexOf(currentMap) + 1;
			if (newIndex == maps.size()) {
				GameMap newMap = generator.generate(newIndex+1);
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
