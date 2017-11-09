package game;

public class GameModel {
	GameMap map;

	public void moveCreature(Creature creature, int[] move) {
		Coordinates currentCoordinates = creature.getCoordinates();
		Tile oldTile = map.getTileAtLocation(currentCoordinates);
		int x = currentCoordinates.getX();
		int y = currentCoordinates.getY();
		x += move[0];
		y += move[1];
		Coordinates newCoordinates = new Coordinates(x, y);
		Tile newTile = map.getTileAtLocation(newCoordinates);

		if (newTile.getCreature() != null) {
			attack(creature, newTile.getCreature());
			takeTurn();
		} else if (newTile.isOccupiable()) {
			oldTile.removeEntity(creature);
			newTile.addEntity(creature);
			creature.setCoordinates(newCoordinates);
			takeTurn();
		} else {
			return;
		}
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

	}
}
