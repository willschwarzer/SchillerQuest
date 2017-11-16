package game;

import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by bruihlera on 11/15/17.
 */
public class MapGenerator {
	private Random random;
	private final int GRID_WIDTH = 5;
	private final int GRID_HEIGHT = 5;
	private final int NUM_ROOMS = 6;
	private GridItem[][] grid;
	private List<int[]> roomLocations;

	public MapGenerator(long seed) {
		random = new Random(seed);
		roomLocations = new ArrayList();
	}
	public MapGenerator() {
		random = new Random();
		roomLocations = new ArrayList();
	}

	public GameMap generate(int difficulty) {
		GameMap map = new GameMap();
		for (int i = 0; i < 25; i++) {
			int[] loc = getNewRoomLocation();
			if (loc != null) {
				roomLocations.add(loc);
			}
		}
		for (int[] loc: roomLocations) {
			List<int[]> connections = getValidConnections(loc);
			for (int[] connection : connections) {
				System.out.println("row: " + loc[0] + "   column: " + loc[1] + "   connection: " + Arrays.toString(connection));
			}
		}

		return map;
	}

	private int[] getNewRoomLocation() {
		int[] location;
		List<int[]> locations = new ArrayList();
		List<int[]> invalidLocations = new ArrayList();
		for (int i = 0; i<GRID_WIDTH; i++) {
			for (int j = 0; j < GRID_HEIGHT; j++) {
				locations.add(new int[] {i, j});
			}
		}
		for (int[] loc: roomLocations) {
			invalidLocations.add(new int[] {loc[0], loc[1]});
			invalidLocations.add(new int[] {loc[0] - 1, loc[1]});
			invalidLocations.add(new int[] {loc[0], loc[1] + 1});
			invalidLocations.add(new int[] {loc[0] + 1, loc[1]});
			invalidLocations.add(new int[] {loc[0], loc[1] - 1});
		}
		int i = 0;
		outerLoop:
		while (i < GRID_HEIGHT*GRID_WIDTH) {
			int randomX = random.nextInt(GRID_WIDTH);
			int randomY = random.nextInt(GRID_HEIGHT);
			location = new int[] {randomX, randomY};

			for (int[] invalidLoc: invalidLocations) {
				if (Arrays.equals(location, invalidLoc)) {
					i++;
					continue outerLoop;
				}
			}
			return location;
		}
		return null;
	}

	private List<int[]> getValidConnections(int[] loc) {
		List<int[]> validConnections = new ArrayList();
		int[] left = new int[] {-1, 0};
		int[] down = new int[] {0, 1};
		int[] right = new int[] {1, 0};
		int[] up = new int[] {0, -1};

		if (loc[1] > 0) {
			validConnections.add(left);
		}
		if (loc[0] < GRID_HEIGHT - 1) {
			validConnections.add(down);
		}
		if (loc[1] < GRID_WIDTH - 1) {
			validConnections.add(right);
		}
		if (loc[0] > 0) {
			validConnections.add(up);
		}

		return validConnections;
	}

	private class GridItem {
		private int[] connections;
		private Tile[] tiles;
	}

	private class Room extends GridItem {
		private int difficulty;

		public Room(int[] connections, int[] location, int difficulty) {
			this.difficulty = difficulty;
		}

		public void generateMonsters() {

		}

		public void generateItems() {

		}

		public void generateFeatures () {

		}
	}

	private class Corridor extends GridItem {
		private Corridor(int[] connections) {

		}
	}
}
