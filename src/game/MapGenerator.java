package game;

import com.sun.javaws.exceptions.InvalidArgumentException;

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
	private Grid grid;
	private List<Room> rooms;

	public MapGenerator(long seed) {
		random = new Random(seed);
		rooms = new ArrayList();
		grid = new Grid(GRID_WIDTH, GRID_HEIGHT);
	}
	public MapGenerator() {
		random = new Random();
		rooms = new ArrayList();
		grid = new Grid(GRID_WIDTH, GRID_HEIGHT);
	}

	public GameMap generate(int difficulty) {
		GameMap map = new GameMap();

		Room upStaircase = new Room(getNewRoomLocation(), difficulty);
		Room downStaircase = new Room(getNewRoomLocation(), difficulty);
		rooms.add(upStaircase);
		rooms.add(downStaircase);

		for (int i = 0; i < 25; i++) {
			int[] loc = getNewRoomLocation();
			if (loc != null) {
				rooms.add(new Room(loc, difficulty));
			}
		}

		for (Room room: rooms) {
			int[] loc = room.getLocation();
			grid.addItem(room);
		}

		/* Test code
		for (int[] loc: roomLocations) {
			List<int[]> connections = getValidConnections(loc);
			for (int[] connection : connections) {
				System.out.println("row: " + loc[0] + "   column: " + loc[1] + "   connection: " + Arrays.toString(connection));
			}
		}*/

		return map;
	}

	protected int[] getNewRoomLocation() {
		int[] location;
		List<int[]> locations = new ArrayList();
		List<int[]> invalidLocations = new ArrayList();
		for (int i = 0; i<GRID_WIDTH; i++) {
			for (int j = 0; j < GRID_HEIGHT; j++) {
				locations.add(new int[] {i, j});
			}
		}
		for (Room room: rooms) {
			int[] loc = room.getLocation();
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

	private class Grid {
		private GridItem[][] grid;

		public Grid(int width, int height) {
			grid = new GridItem[height][width];
		}

		public void addItem(GridItem item) {
			int[] loc = item.getLocation();
			if (grid[loc[0]][loc[1]] != null) {
				throw new IllegalArgumentException();
			} else {
				grid[loc[0]][loc[1]] = item;
			}
		}

		public GridItem getGridItemAtLocation(int[] loc) {
			return grid[loc[0]][loc[1]];
		}

		public char[][] toCharArray() {
			return null;
		}
	}

	private abstract class GridItem {
		private List<int[]> connections;
		private Tile[][] tiles;
		private int[] location;
		private final int ITEM_WIDTH = 8;
		private final int ITEM_HEIGHT = 8;

		private GridItem(int[] location) {
			this.location = location;
		}

		protected List<int[]> getValidConnections(int[] loc) {
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

		public int[] getLocation() {
			return location;
		}

		public Tile[][] getTiles() {
			return tiles;
		}

		public List<int[]> getConnections() {
			return connections;
		}
	}

	private class Room extends GridItem {
		private int difficulty;
		private List<int[]> connections;

		public Room(int[] location, int difficulty) {
			super(location);
			this.difficulty = difficulty;
			connections = getValidConnections(location);
			generateTerrain();
			generateFeatures();
			generateMonsters();
			generateItems();
		}

		private void generateMonsters() {

		}

		private void generateItems() {

		}

		private void generateFeatures () {

		}

		public void addUpStaircase () {

		}

		public void addDownStaircase () {

		}

		private void generateTerrain() {

		}
	}

	private class Corridor extends GridItem {
		private Corridor(int[] location) {
			super(location);
			generateTerrain();
		}

		private void generateTerrain() {

		}
	}
}
