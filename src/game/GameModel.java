package game;

import monsters.*;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class GameModel implements Subject {
	private GameMap map;
	private ControllerInterface controller;
	private List<Observer> observers;

	public GameModel(Controller controller) {
		observers = new ArrayList<>();
		this.controller = controller;
		addObserver(controller);

		Coordinates startCoordinates = new Coordinates(10, 3);
		loadNewLevel("src/resources/level1.txt", startCoordinates);

		notifyObservers();
	}

	public GameModel(Controller controller, GameMap map, Coordinates startCoordinates) {
		observers = new ArrayList<>();
		this.controller = controller;
		addObserver(controller);

		this.map = map;
		spawnPlayer(startCoordinates);

		notifyObservers();
	}

	private void loadNewLevel(String fileName, Coordinates startCoordinates) {
		map = new GameMap(new File(fileName));
		spawnPlayer(startCoordinates);
	}

	/**
	 * Moves a given creatures location with given move
	 *
	 * @param creature The creature being moved
	 * @param move     The changes in the creatures location
	 */
	public void moveCreature(Creature creature, int[] move) {
		Coordinates currentCoordinates = creature.getCoordinates();
		Tile oldTile = map.getTileAtLocation(currentCoordinates);
		int x = currentCoordinates.getX();
		int y = currentCoordinates.getY();
		x += move[0];
		y += move[1];
		Coordinates destinationCoordinates = new Coordinates(x, y);
		Tile newTile = map.getTileAtLocation(destinationCoordinates);

		if (newTile.getCreature() != null) {
			attack(creature, newTile.getCreature());
			if(newTile.getCreature().getStats().getHealth() <= 0) {
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

	public void setController(ControllerInterface controller) {
		this.controller = controller;
	}

	public void setGameMap(GameMap map) {
		this.map = map;
	}

	public Player getPlayer() {
		return map.getPlayer();
	}

	public void attack(Creature attacker, Creature attackee) {
		int attack = attacker.getStats().getAttack();
		if (attacker.getEquipped().containsKey("weapon")){
			InventoryItem attackerWeapon = attacker.getEquipped().get("weapon");
			attack += attackerWeapon.getBoost();
		}

		int defense = attackee.getStats().getDefense();
		if (attackee.getEquipped().containsKey("armor")){
			InventoryItem attackeeArmor = attackee.getEquipped().get("armor");
			defense += attackeeArmor.getBoost();
		}

		if (attackee.getEquipped().containsKey("shield")){
			InventoryItem shield = attackee.getEquipped().get("shield");
			defense =+ shield.getBoost();
		}

		int attackeeSpd = attackee.getStats().getSpeed();
		int attackerSpd = attacker.getStats().getSpeed();

		if (attackee.getEquipped().containsKey("shoes")){
			InventoryItem attackeeShoes = attackee.getEquipped().get("shoes");
			attackeeSpd += attackeeShoes.getBoost();
		}
		if (attacker.getEquipped().containsKey("shoes")){
			InventoryItem attackerShoes = attacker.getEquipped().get("shoes");
			attackeeSpd += attackerShoes.getBoost();
		}

		int hitChance = randomWithRange(attackerSpd/2, attackerSpd) - randomWithRange(attackeeSpd/4, attackeeSpd);

		int damage = 0;
		if(hitChance > 0){
			damage += randomWithRange(attack/2, attack) - randomWithRange(defense/3, defense);
		} else {System.out.println(attacker.getName()+ "'s attack missed");}

		if(damage > 0){
			attackee.getStats().setHealth(attackee.getStats().getHealth() - damage);
			System.out.println(attacker.getName() +"'s attack did " + damage + " point(s) of damage to " + attackee.getName());
		} else if(hitChance > 0) { 
			System.out.println(attacker.getName()+ "'s attack did no damage to " + attackee.getName());
		}
	}

	/**
	 * Will allow all of the other active entities to take a turn
	 */
	public void takeTurn() {
		List<Monster> monsters = map.getMonsters();

		for (int i = 0; i < monsters.size(); i++) {
			moveCreature(monsters.get(i), monsters.get(i).getMove());
		}
		notifyObservers();
	}

	/**
	 * Creates a Player at given coordinate location
	 *
	 * @param coordinates The coordinate the player will be spawned at
	 */
	public void spawnPlayer(Coordinates coordinates) {
		//TODO: Fix hard coding in the beginning stats
		Stats playerStats = new Stats(100,10,10,10,4);
		Player player = new Player(coordinates, playerStats);
		map.setPlayer(player);
	}

	/**
	 * Creates a Monster at given coordinate location.
	 * This will be replaced with automatic generation of monster on each level
	 *
	 * @param coordinates The coordinate the player will be spawned at
	 */
	public void spawnMonster(Coordinates coordinates, int level) {


		Rat monster = new Rat(coordinates, map, level);
		map.setMonster(monster);
	}

	public boolean addObserver(Observer observer) {
		return observers.add(observer);
	}

	public boolean removeObserver(Observer o) {
		return observers.remove(o);
	}

	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(map.getVisionAsCharArray(getPlayer().getCoordinates(), getPlayer().getStats().getVision
					()));
		}
	}

	private int randomWithRange(int min, int max)
	{
		int range = (max - min) + 1;
		int value = (int)(Math.random() * range) + min;
		return value;
	}

	private void creatureDeath(Creature dead, Tile newTile ){
		System.out.println(dead.getName() + " died");

		if(dead == getPlayer()){
			System.out.println("Game Over");
			//GameOver
		}else{
			map.removeMonster(dead);
			newTile.removeEntity(dead);
		}
	}
}
