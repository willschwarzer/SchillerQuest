package game;

import java.util.*;

public class MapGenerator {
	private final Random random;
	private final long initialRandomSeed;
	/**
	 * Currently must be the same as GRID_HEIGHT
	 *
	 * @see #GRID_HEIGHT
	 */
	private final int GRID_WIDTH = 4;
	/**
	 * Currently must be the same as GRID_WIDTH
	 *
	 * @see #GRID_HEIGHT
	 */
	private final int GRID_HEIGHT = 3;
	/**
	 * Currently must be the same as ITEM_HEIGHT
	 *
	 * @see #ITEM_HEIGHT
	 */
	private final int ITEM_WIDTH = 9;
	/**
	 * Currently must be the same as ITEM_WIDTH
	 *
	 * @see #ITEM_WIDTH
	 */
	private final int ITEM_HEIGHT = 9;
	private static final int LEFT_CONNECTION = 0;
	private static final int DOWN_CONNECTION = 1;
	private static final int RIGHT_CONNECTION = 2;
	private static final int UP_CONNECTION = 3;
	private Grid grid;
	private List<Room> rooms;

	/**
	 * Creates a new MapGenerator with the given Random seed.
	 *
	 * @param seed the seed for the internal Random that determines the (reproducible) generation of the MapGenerator.
	 */
	public MapGenerator(long seed) {
		initialRandomSeed = seed;
		random = new Random(seed);
		rooms = new ArrayList<>();
	}

	/**
	 * Creates a new MapGenerator with a pseudorandom Random seed (System.currentTimeMillis())
	 *
	 * @see #MapGenerator(long)
	 */
	public MapGenerator() {
		this(System.currentTimeMillis());
	}

	/**
	 * Gets Coordinates to spawn at, which are inside the generated structure (does not check if there's already a
	 * Creature there).  Returned Coordinates are the center of the top left room/corridor.
	 *
	 * @return Coordinates Coordinates to spawn at
	 */
	public Coordinates getSpawnLocation() {
		return new Coordinates(ITEM_WIDTH / 2, ITEM_HEIGHT / 2);
	}

	/**
	 * Get the seed that was used to start the Random.
	 *
	 * @return the seed that was used to start the Random
	 */
	public long getInitialRandomSeed() {
		return initialRandomSeed;
	}

	public GameMap generate(int difficulty) {
		grid = new Grid(GRID_WIDTH, GRID_HEIGHT);
		rooms = new ArrayList();

		Room upStaircaseRoom = new Room(getNewRoomLocation(), difficulty);
		upStaircaseRoom.addUpStaircase();
		grid.addItem(upStaircaseRoom);
		rooms.add(upStaircaseRoom);

		Room downStaircaseRoom = new Room(getNewRoomLocation(), difficulty);
		downStaircaseRoom.addDownStaircase();
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
			int randomRows = random.nextInt(GRID_HEIGHT);
			int randomCols = random.nextInt(GRID_WIDTH);
			location = new int[]{randomRows, randomCols};

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
						System.arraycopy(itemTiles[tileRow], 0, mapTiles[tileRow + itemRow * ITEM_HEIGHT],
								itemCol * ITEM_WIDTH, ITEM_WIDTH);
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

		private GridItem(int[] location) {
			this.location = location;
			this.connections = getValidConnections(location);
			this.tiles = new Tile[ITEM_WIDTH][ITEM_HEIGHT];
		}

		protected boolean[] getValidConnections(int[] loc) {
			boolean[] validConnections = new boolean[4];

			validConnections[LEFT_CONNECTION] = (loc[1] > 0);
			validConnections[DOWN_CONNECTION] = (loc[0] < GRID_HEIGHT - 1);
			validConnections[RIGHT_CONNECTION] = (loc[1] < GRID_WIDTH - 1);
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

		/**
		 * Gets an occupiable Tile in the GridItem.  Prefers to return a completely open spot over a spot that is
		 * occupiable but having another object.  Throws a RuntimeException if no occupiable location is found.
		 *
		 * @return Location of an occupiable Tile
		 */
		public Coordinates getOccupiableCoordinates() {
			for (int i = 0; i < ITEM_HEIGHT*ITEM_WIDTH; i++) {
				// Only place entities INSIDE of rooms, not in doorways
				int x = random.nextInt(ITEM_WIDTH-2) + 1;
				int y = random.nextInt(ITEM_HEIGHT-2)+1;
				if (tiles[y][x].isOccupiableAndEmpty() &&
						!tiles[y][x].hasUpStaircase()) {
					return new Coordinates(x, y);
				}
			}
			return null;
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
				if (spawn != null) {
					Tile tile = getTiles()[spawn.getX()][spawn.getY()];
					tile.addEntity(mob);
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
			Coordinates coord = getOccupiableCoordinates();
			getTiles()[coord.getX()][coord.getY()] =
					new Tile(new Terrain('<'));
		}

		public void addDownStaircase() {
			Coordinates coord = getOccupiableCoordinates();
			getTiles()[coord.getX()][coord.getY()] =
					new Tile(new Terrain('>'));
		}

		private void generateTerrain() {
			Tile[][] tiles = getTiles();

			Tile[] leftWall = new Tile[ITEM_HEIGHT - 2];
			Tile[] bottomWall = new Tile[ITEM_WIDTH];
			Tile[] rightWall = new Tile[ITEM_HEIGHT - 2];
			Tile[] topWall = new Tile[ITEM_WIDTH];

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
			tiles[ITEM_HEIGHT - 1] = bottomWall;

			for (int row = 1; row < ITEM_HEIGHT - 1; row++) {
				tiles[row][0] = leftWall[row - 1];
				tiles[row][ITEM_WIDTH - 1] = rightWall[row - 1];

				for (int col = 1; col < ITEM_WIDTH - 1; col++) {
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
			int horMidLine = ITEM_HEIGHT / 2;
			int vertMidLine = ITEM_WIDTH / 2;

			boolean[] connections = getConnections();

			if (connections[LEFT_CONNECTION]) {
				for (int col = 0; col < vertMidLine; col++) {
					tiles[horMidLine - 1][col] = new Tile(new Terrain('#'));
					tiles[horMidLine + 1][col] = new Tile(new Terrain('#'));
				}
			} else {
				tiles[horMidLine][vertMidLine - 1] = new Tile(new Terrain('#'));
			}
			if (connections[DOWN_CONNECTION]) {
				for (int row = horMidLine + 1; row < ITEM_HEIGHT; row++) {
					tiles[row][vertMidLine - 1] = new Tile(new Terrain('#'));
					tiles[row][vertMidLine + 1] = new Tile(new Terrain('#'));
				}
			} else {
				tiles[horMidLine + 1][vertMidLine] = new Tile(new Terrain('#'));
			}
			if (connections[RIGHT_CONNECTION]) {
				for (int col = vertMidLine + 1; col < ITEM_WIDTH; col++) {
					tiles[horMidLine - 1][col] = new Tile(new Terrain('#'));
					tiles[horMidLine + 1][col] = new Tile(new Terrain('#'));
				}
			} else {
				tiles[horMidLine][vertMidLine + 1] = new Tile(new Terrain('#'));
			}
			if (connections[UP_CONNECTION]) {
				for (int row = 0; row < horMidLine; row++) {
					tiles[row][vertMidLine - 1] = new Tile(new Terrain('#'));
					tiles[row][vertMidLine + 1] = new Tile(new Terrain('#'));
				}
			} else {
				tiles[horMidLine - 1][vertMidLine] = new Tile(new Terrain('#'));
			}

			for (int row = 0; row < ITEM_HEIGHT; row++) {
				for (int col = 0; col < ITEM_WIDTH; col++) {
					if (tiles[row][col] == null) {
						tiles[row][col] = new Tile(new Terrain(' '));
					}
				}
			}
		}
	}
}
