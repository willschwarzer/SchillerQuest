package game;

import java.util.ArrayList;
import java.util.List;

public abstract class Monster extends Creature {

	public Monster(Coordinates coordinates, GameMap map) {
		super(coordinates, map);
	}

	public int[] getMove() {
		int[] move;
		List<int[]> validDirections = new ArrayList<>();
		Coordinates selfCoordinates = getCoordinates();
		GameMap map = getGameMap();

		int[] left = {-1, 0};
		int[] down = {0, 1};
		int[] right = {1, 0};
		int[] up = {0, -1};
		int selfX = selfCoordinates.getX();
		int selfY = selfCoordinates.getY();
		if (map.getTileAtLocation(new Coordinates(selfX + -1, selfY + 0)).isOccupiable()) {
			validDirections.add(left);
		} else if (map.getTileAtLocation(new Coordinates(selfX + 0, selfY + 1)).isOccupiable()) {
			validDirections.add(down);
		} else if (map.getTileAtLocation(new Coordinates(selfX + 1, selfY + 0)).isOccupiable()) {
			validDirections.add(right);
		} else if (map.getTileAtLocation(new Coordinates(selfX + 0, selfY + 1)).isOccupiable()) {
			validDirections.add(up);
		}

		Player player = map.getPlayer();
		Coordinates playerCoordinates = player.getCoordinates();
		int playerX = playerCoordinates.getX();
		int playerY = playerCoordinates.getY();
		int deltaX = playerX - selfX;
		int deltaY = playerY - selfY;

		if (deltaX > 5 || deltaY > 5) {
			int randomIndex = (int) Math.random()*validDirections.size();
			move = validDirections.get(randomIndex);
		} else if (Math.abs(deltaX) > (deltaY) && deltaX > 0 && validDirections.contains(right)) {
			move = right;
		} else if (Math.abs(deltaX) > (deltaY) && deltaX < 0 && validDirections.contains(left)) {
			move = left;
		} else if (Math.abs(deltaX) < (deltaY) && deltaY > 0 && validDirections.contains(up)) {
			move = up;
		} else if (Math.abs(deltaX) < (deltaY) && deltaY > 0 && validDirections.contains(down)) {
			move = down;
		} else {
			int randomIndex = (int) Math.random()*validDirections.size();
			move = validDirections.get(randomIndex);
		}
		return move;
	}
}
