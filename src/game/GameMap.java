package game;

import java.io.*;

/**
 * Class to store the information about the current map - including the terrain, items, and creatures present.
 */
public class GameMap implements GameMapInterface {
	/**
	 * Stores the map tiles in a row-major 2D Tile array.  With (0, 0) at top left, point (col x, row y) is at
	 * map[row y][col x]
	 */
	private Tile[][] map;
	private Player player;

	/**
	 * Creates a GameMap from a given File.  The file must have lines of uniform length.
	 *
	 * @param file The File to build the map from
	 */
	public GameMap(File file) {
		buildMapFromFile(file);
	}

	/**
	 * Builds the map from the given File.  The file must have lines of uniform length.
	 *
	 * @param file The File to build the map from
	 * @return Whether the GameMap was built from the File successfully
	 */
	private boolean buildMapFromFile(File file) {
		if (map != null) {
			throw new IllegalStateException("Cannot build GameMap from a file when the map already exists.");
		}

		int height = getFileLength(file);
		int width = getFileWidth(file);

		if (height == 0) {
			throw new IllegalArgumentException("Invalid input file - height is 0 (file " + file);
		} else if (width == 0) {
			throw new IllegalArgumentException("Invalid input file - width is 0 (file " + file);
		} else {
			map = new Tile[height][width];

			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				for (int row = 0; row < height; row++) {
					String currentLine = reader.readLine();
					if (currentLine.length() != width) {
						throw new IllegalArgumentException(
								"Line " + row + " in file " + file + "has incorrect width (expected " + width + " " +
								"but was " + currentLine.length() + ")");
					} else {
						for (int col = 0; col < currentLine.length(); col++) {
							Terrain terrain = new Terrain(currentLine.charAt(col));
							Tile tile = new Tile(terrain);
							map[row][col] = tile;
						}
					}
				}
				return true;
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("File not found", e);
			} catch (IOException e) {
				throw new IllegalArgumentException("IOException", e);
			}
		}
	}

	/**
	 * Prints out the map to the console.
	 */
	public void printMapToConsole() {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[0].length; col++) {
				System.out.print(map[row][col].getMapGraphic());
			}
			System.out.println();
		}
	}

	/**
	 * Returns the current view of the GameMap as a row-major 2D char array.  With (0, 0) at top left, point (col x,
	 * row y) is at map[row y][col x].  This only displays the 'top' of each Tile.
	 *
	 * @return the current view of the GameMap as a row-major 2D char array
	 */
	@Override
	public char[][] getMapAsCharArray() {
		char[][] array = new char[map.length][map[0].length];

		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[0].length; col++) {
				array[row][col] = map[row][col].getMapGraphic();
			}
		}

		return array;
	}

	@Override
	public Tile[][] getSquareAreaAroundLocation(Coordinates coordinates, int distance) {
		// TODO implement
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Tile[][] getRectangularAreaAroundLocation(Coordinates coordinates, int width, int height) {
		// TODO implement
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Tile[][] getCircularAreaAroundLocation(Coordinates coordinates, int radius) {
		// TODO implement
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public Tile getTileAtLocation(Coordinates coordinates) {
		if (coordinates.getX() > map[0].length) {
			// TODO figure out how to handle
			throw new IllegalArgumentException("X coordinate too large");
		}
		if (coordinates.getY() > map.length) {
			// TODO figure out how to handle
			throw new IllegalArgumentException("Y coordinate too large");
		}
		return map[coordinates.getY()][coordinates.getX()];
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;

		Tile location = map[player.getCoordinates().getY()][player.getCoordinates().getX()];
		location.addEntity(player);
	}

	/**
	 * Gets the height of the given file.
	 *
	 * @param file File to get the height of
	 * @return Height of the given file
	 */
	private int getFileLength(File file) {
		try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
			while (reader.readLine() != null) {
			}
			return reader.getLineNumber();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.", e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Gets the width of the first line of the given file
	 *
	 * @param file File to get the width of
	 * @return Width of the given file
	 */
	private int getFileWidth(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			return br.readLine().length();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.", e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}