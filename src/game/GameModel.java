package game;

import java.util.ArrayList;

public class GameModel {
	GameMap map;
	private ControllerInterface controller;

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

	public void takeTurn() {
//		map.printMapToConsole();
		System.out.println(map.getMapAsCharArray());
		controller.updateViewGrid(map.getMapAsCharArray());
	}

	public void spawnPlayer(Coordinates coordinates) {
		Player player = new Player(coordinates);
		map.setPlayer(player);
	}
}
