package game;

import java.util.ArrayList;
import java.util.List;

public abstract class Monster extends Creature {

	public Monster(Coordinates coordinates, GameMap map, int level) {
		super(coordinates, map, level);
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
		}
		if (map.getTileAtLocation(new Coordinates(selfX + 0, selfY + 1)).isOccupiable()) {
			validDirections.add(down);
		}
		if (map.getTileAtLocation(new Coordinates(selfX + 1, selfY + 0)).isOccupiable()) {
			validDirections.add(right);
		}
		if (map.getTileAtLocation(new Coordinates(selfX + 0, selfY - 1)).isOccupiable()) {
			validDirections.add(up);
		}

		Player player = map.getPlayer();
		Coordinates playerCoordinates = player.getCoordinates();
		int playerX = playerCoordinates.getX();
		int playerY = playerCoordinates.getY();
		int deltaX = playerX - selfX;
		int deltaY = playerY - selfY;

		for (int[] array : validDirections) {
			System.out.println("Can move:   " + array[0] + "     " + array[1]);
		}

		if (Math.abs(deltaX) > 10 || Math.abs(deltaY) > 10) {
			int randomIndex = (int) (Math.random() * validDirections.size());
			move = validDirections.get(randomIndex);
		} else if (deltaX > Math.abs(deltaY) && validDirections.contains(right)) {
			move = right;
		} else if (-1 * deltaX > Math.abs(deltaY) && validDirections.contains(left)) {
			move = left;
		} else if (Math.abs(deltaX) <= -1 * deltaY && validDirections.contains(up)) {
			move = up;
		} else if (Math.abs(deltaX) <= deltaY && validDirections.contains(down)) {
			move = down;
		} else {
			int randomIndex = (int) (Math.random() * validDirections.size());
			move = validDirections.get(randomIndex);
		}
		System.out.println(move[0] + "      " + move[1]);
		return move;
	}
}
