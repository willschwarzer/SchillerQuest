package game;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

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
			takeTurn();
		} else if (newTile.isOccupiable()) {
			oldTile.removeEntity(creature);
			newTile.addEntity(creature);
			creature.setCoordinates(destinationCoordinates);
			takeTurn();
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
	}

	/**
	 * Will allow all of the other active entities to take a turn
	 */
	public void takeTurn() {
		notifyObservers();
	}

	/**
	 * Creates a Player at given coordinate location
	 *
	 * @param coordinates The coordinate the player will be spawned at
	 */
	public void spawnPlayer(Coordinates coordinates) {
		Player player = new Player(coordinates);
		map.setPlayer(player);
	}

	public boolean addObserver(Observer observer) {
		return observers.add(observer);
	}

	public boolean removeObserver(Observer o) {
		return observers.remove(o);
	}

	public void notifyObservers() {
		for (Observer observer : observers) {
			observer.update(map.getVisionAsCharArray(getPlayer().getCoordinates(), getPlayer().getStats().getVision()));
		}
	}
}
