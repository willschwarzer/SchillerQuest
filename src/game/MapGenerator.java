package game;

import java.util.*;

public class MapGenerator {
	private final Random random;
	private final long initialRandomSeed;
	private final int GRID_WIDTH = 5;
	private final int GRID_HEIGHT = 5;
	private final int NUM_ROOMS = 6;
	private final int LEFT_CONNECTION = 0;
	private final int DOWN_CONNECTION = 1;
	private final int RIGHT_CONNECTION = 2;
	private final int UP_CONNECTION = 3;
	private Grid grid;
	private List<Room> rooms;

	/**
	 * Creates a new MapGenerator with the given Random seed.
	 * @param seed the seed for the internal Random that determines the (reproducible) generation of the MapGenerator.
	 */
	public MapGenerator(long seed) {
		initialRandomSeed = seed;
		random = new Random(seed);
		rooms = new ArrayList<>();
		grid = new Grid(GRID_WIDTH, GRID_HEIGHT);
	}

	/**
	 * Creates a new MapGenerator with a pseudorandom Random seed (System.currentTimeMillis())
	 * @see #MapGenerator(long)
	 */
	public MapGenerator() {
		initialRandomSeed = System.currentTimeMillis();
		random = new Random(initialRandomSeed);
		rooms = new ArrayList<>();
		grid = new Grid(GRID_WIDTH, GRID_HEIGHT);
	}

	public GameMap generate(int difficulty) {
		Room upStaircaseRoom = new Room(getNewRoomLocation(), difficulty);
		grid.addItem(upStaircaseRoom);
		rooms.add(upStaircaseRoom);

		Room downStaircaseRoom = new Room(getNewRoomLocation(), difficulty);
		grid.addItem(downStaircaseRoom);
		rooms.add(downStaircaseRoom);

		for (int i = 0; i < 25; i++) {
			int[] location = getNewRoomLocation();
			if (location != null) {
				Room room = new Room(location, difficulty);
				grid.addItem(room);
				rooms.add(room);
			}
		}

		for (int row = 0; row < GRID_HEIGHT; row++) {
			for (int col = 0; col < GRID_WIDTH; col++) {
				if (grid.getGridItemAtLocation(new int[]{row, col}) == null) {
					GridItem corridor = new Corridor(new int[]{row, col});
					grid.addItem(corridor);
				}
			}
		}

		Tile[][] tiles = grid.toTileArray();

		GameMap map = new GameMap(tiles);

		return map;
	}

	protected int[] getNewRoomLocation() {
		int[] location;
		List<int[]> locations = new ArrayList<>();
		List<int[]> invalidLocations = new ArrayList<>();
		for (int i = 0; i < GRID_WIDTH; i++) {
			for (int j = 0; j < GRID_HEIGHT; j++) {
				locations.add(new int[]{i, j});
			}
		}
		for (Room room : rooms) {
			int[] loc = room.getLocation();
			invalidLocations.add(new int[]{loc[0], loc[1]});
			invalidLocations.add(new int[]{loc[0] - 1, loc[1]});
			invalidLocations.add(new int[]{loc[0], loc[1] + 1});
			invalidLocations.add(new int[]{loc[0] + 1, loc[1]});
			invalidLocations.add(new int[]{loc[0], loc[1] - 1});
		}
		int i = 0;
		outerLoop:
		while (i < GRID_HEIGHT * GRID_WIDTH) {
			int randomX = random.nextInt(GRID_WIDTH);
			int randomY = random.nextInt(GRID_HEIGHT);
			location = new int[]{randomX, randomY};

			for (int[] invalidLoc : invalidLocations) {
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
		private final int ITEM_WIDTH = 9;
		private final int ITEM_HEIGHT = 9;

		public Grid(int width, int height) {
			if (width <= 0) {
				throw new IllegalArgumentException("Cannot create a Grid with width " + width);
			}
			if (height <= 0) {
				throw new IllegalArgumentException("Cannot create a Grid with height " + height);
			}

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

		public Tile[][] toTileArray() {
			Tile[][] mapTiles = new Tile[GRID_HEIGHT * ITEM_HEIGHT][GRID_WIDTH * ITEM_WIDTH];

			for (int itemRow = 0; itemRow < GRID_HEIGHT; itemRow++) {
				for (int itemCol = 0; itemCol < GRID_WIDTH; itemCol++) {
					Tile[][] itemTiles = grid[itemRow][itemCol].getTiles();

					for (int tileRow = 0; tileRow < ITEM_HEIGHT; tileRow++) {
						for (int tileCol = 0; tileCol < ITEM_WIDTH; tileCol++) {
							mapTiles[tileRow + itemRow * ITEM_HEIGHT][tileCol + itemCol * ITEM_WIDTH] = itemTiles[tileRow][tileCol];
						}
					}
				}
			}
			return mapTiles;
		}
	}

	private abstract class GridItem {
		private boolean[] connections;
		private Tile[][] tiles;
		private int[] location;
		private int width = 9;
		private int height = 9;

		private GridItem(int[] location) {
			this.location = location;
			this.connections = getValidConnections(location);
			this.tiles = new Tile[width][height];
		}

		protected boolean[] getValidConnections(int[] loc) {
			boolean[] validConnections = new boolean[4];

			validConnections[LEFT_CONNECTION] = (loc[1] > 0);
			validConnections[DOWN_CONNECTION] = (loc[0] < GRID_HEIGHT - 1);
			validConnections[RIGHT_CONNECTION] = (loc[1] < GRID_HEIGHT - 1);
			validConnections[UP_CONNECTION] = (loc[0] > 0);

			return validConnections;
		}

		public int[] getLocation() {
			return location;
		}

		public Tile[][] getTiles() {
			return tiles;
		}

		public boolean[] getConnections() {
			return connections;
		}

		public int getItemWidth() {
			return width;
		}

		public int getItemHeight() {
			return height;
		}

		/**
		 * Gets an occupiable Tile in the GridItem.  Throws a RuntimeException if no occupiable location is found.
		 *
		 * @return Location of an occupiable Tile
		 */
		public Coordinates getOccupiableCoordinates() {
			for (int i = 0; i < 1000; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				if (tiles[y][x].isOccupiable()) {
					return new Coordinates(x, y);
				}
			}
			throw new RuntimeException("No occupiable location found in the GridItem with 1000 tries.");
		}
	}

	private class Room extends GridItem {
		private int difficulty;

		public Room(int[] location, int difficulty) {
			super(location);
			this.difficulty = difficulty;
			generateTerrain();
			generateFeatures();
			generateMonsters();
			generateItems();
		}

		/**
		 * First generates a List of Monsters that should be spawned in the Room for the given difficulty, and then
		 * spawns them in.
		 */
		private void generateMonsters() {
			List<Monster> monsters = Monster.getAppropriateMonsters(random, difficulty);

			for (Monster mob : monsters) {
				Coordinates spawn = getOccupiableCoordinates();
				if (!getTiles()[spawn.getY()][spawn.getX()].addEntity(mob)) {
					throw new RuntimeException(
							"Could not add a Monster to a tile that was determined to be occupiable previously, huh?");
				}
			}
		}

		private void generateItems() {
			List<Item> items = Item.getAppropriateItems(random, difficulty);

			for (Item item : items) {
				Coordinates spawn = getOccupiableCoordinates();
				if (!getTiles()[spawn.getY()][spawn.getX()].addEntity(item)) {
					throw new RuntimeException(
							"Could not add an Item to a tile that was determined to be occupiable previously, huh?");
				}
			}
		}

		private void generateFeatures() {

		}

		public void addUpStaircase() {

		}

		public void addDownStaircase() {

		}

		private void generateTerrain() {
			Tile[][] tiles = getTiles();

			int width = getItemWidth();
			int height = getItemHeight();

			Tile[] leftWall = new Tile[height - 2];
			Tile[] bottomWall = new Tile[width];
			Tile[] rightWall = new Tile[height - 2];
			Tile[] topWall = new Tile[width];

			boolean[] connections = getConnections();
			for (int i = 0; i < leftWall.length; i++) {
				leftWall[i] = new Tile(new Terrain('#'));
			}
			if (connections[LEFT_CONNECTION]) {
				leftWall[leftWall.length / 2] = new Tile(new Terrain(' '));
			}

			for (int i = 0; i < bottomWall.length; i++) {
				bottomWall[i] = new Tile(new Terrain('#'));
			}
			if (connections[DOWN_CONNECTION]) {
				bottomWall[bottomWall.length / 2] = new Tile(new Terrain(' '));
			}

			for (int i = 0; i < rightWall.length; i++) {
				rightWall[i] = new Tile(new Terrain('#'));
			}
			if (connections[RIGHT_CONNECTION]) {
				rightWall[rightWall.length / 2] = new Tile(new Terrain(' '));
			}

			for (int i = 0; i < topWall.length; i++) {
				topWall[i] = new Tile(new Terrain('#'));
			}
			if (connections[UP_CONNECTION]) {
				topWall[topWall.length / 2] = new Tile(new Terrain(' '));
			}

			tiles[0] = topWall;
			tiles[height - 1] = bottomWall;

			for (int row = 1; row < height - 1; row++) {
				tiles[row][0] = leftWall[row - 1];
				tiles[row][width - 1] = rightWall[row - 1];

				for (int col = 1; col < width - 1; col++) {
					tiles[row][col] = new Tile(new Terrain(' '));
				}
			}
		}
	}

	private class Corridor extends GridItem {
		private Corridor(int[] location) {
			super(location);
			generateTerrain();
		}

		private void generateTerrain() {
			//temp implementation
			Tile[][] tiles = getTiles();

			int width = getItemWidth();
			int height = getItemHeight();

			for (int row = 0; row < height; row++) {
				for (int col = 0; col < width; col++) {
					tiles[row][col] = new Tile(new Terrain(' '));
				}
			}
		}
	}
}
