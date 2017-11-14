package game;

public class MonsterAI {
	private GameMap map;

	public int[] getMove() {
		int[] move;
		Player player = map.getPlayer();
		Coordinates playerCoordinates = player.getCoordinates();
		int playerX = playerCoordinates.getX();
		int playerY = playerCoordinates.getY();
		int selfX = this.coordinates.getX();


		return move;
	}
}
