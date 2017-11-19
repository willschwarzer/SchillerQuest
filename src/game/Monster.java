package game;

import game.monsters.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Monster extends Creature {

	public Monster(Coordinates coordinates, GameMap map, int level) {
		super(coordinates, map, level);
	}

	public Monster(Coordinates coordinates, int level) {
		super(coordinates, level);
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
		if (map.getTileAtLocation(new Coordinates(selfX + -1, selfY + 0)).isOccupiableTerrain()) {
			validDirections.add(left);
		}
		if (map.getTileAtLocation(new Coordinates(selfX + 0, selfY + 1)).isOccupiableTerrain()) {
			validDirections.add(down);
		}
		if (map.getTileAtLocation(new Coordinates(selfX + 1, selfY + 0)).isOccupiableTerrain()) {
			validDirections.add(right);
		}
		if (map.getTileAtLocation(new Coordinates(selfX + 0, selfY - 1)).isOccupiableTerrain()) {
			validDirections.add(up);
		}

		Player player = map.getPlayer();
		Coordinates playerCoordinates = player.getCoordinates();
		int playerX = playerCoordinates.getX();
		int playerY = playerCoordinates.getY();
		int deltaX = playerX - selfX;
		int deltaY = playerY - selfY;

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
		return move;
	}

	/**
	 * Gets a List of Monsters appropriate for the given difficulty using the given Random.
	 *
	 * @param random     Random to use in Monster generation.
	 * @param difficulty Difficulty to generate with.
	 * @return The generated Monsters for the given difficulty.
	 */
	public static List<Monster> getAppropriateMonsters(Random random, int difficulty) {
		// TODO improve this
		List<Monster> monsters = new ArrayList<>();

		for (int i = 0; i < difficulty; i++) {
			monsters.add(createMonster(random, difficulty));
		}

		return monsters;
	}

	/**
	 * Creates an individual Monster for the given difficulty using the given Random.
	 *
	 * @param random     Random to use in Monster generation.
	 * @param difficulty Difficulty to generate with, set as the Monster's level.
	 * @return The generated Monster for the given difficulty.
	 */
	private static Monster createMonster(Random random, int difficulty) {
		// TODO make this actually work and return a random Monster
		return new Rat(difficulty);
	}
}
