package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameMap implements GameMapInterface {
	/**
	 * Stores the map tiles in a row-major 2D array.  With (0, 0) at top left, point (col x, row y) is at
	 * map[row y][col x]
	 */
	private char[][] map;

	/**
	 * Creates a GameMap from a given file.  The file must have lines of uniform length.
	 *
	 * @param file The file to build the map from
	 */
	public GameMap(File file) {
		buildMapFromFile(file);
	}

	/**
	 * Builds the map from the given file.  The file must have lines of uniform length.
	 *
	 * @param file File to build the map from
	 * @return
	 */
	private boolean buildMapFromFile(File file) {
		int height = getFileLength(file);
		int width = getFileWidth(file);

		if (height == 0) {
			throw new IllegalArgumentException("Invalid input file - height is 0 (file " + file);
			// TODO figure out how to handle this
		} else if (width == 0) {
			throw new IllegalArgumentException("Invalid input file - width is 0 (file " + file);
			// TODO figure out how to handle this
		} else {
			map = new char[height][width];

			try (Scanner scanner = new Scanner(file)) {
				for (int row = 0; row < height; row++) {
					String currentLine = scanner.nextLine();
					if (currentLine.length() != width) {
						throw new IllegalArgumentException(
								"Line " + row + " in file " + file + "has incorrect width (expected " + width + " " +
								"but was " + currentLine.length() + ")");
					} else {
						for (int col = 0; col < currentLine.length(); col++) {
							map[row][col] = currentLine.charAt(col);
						}
					}
				}
				return true;
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("File not found.", e);
			}
		}
	}

	/**
	 * Gets the height of the given file.
	 *
	 * @param file File to get the height of
	 * @return Height of the given file
	 */
	private int getFileLength(File file) {
		try (Scanner scanner = new Scanner(file)) {
			int length = 0;
			while (scanner.hasNextLine()) {
				length++;
				scanner.nextLine();
			}
			return length;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.", e);
		}
	}

	/**
	 * Gets the width of the first line of the given file
	 *
	 * @param file File to get the width of
	 * @return Width of the given file
	 */
	private int getFileWidth(File file) {
		try (Scanner scanner = new Scanner(file)) {
			int width = scanner.nextLine().length();
			scanner.close();
			return width;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.", e);
		}
	}

	/**
	 * Prints out the map to the console.
	 */
	public void printMapToConsole() {
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[0].length; col++) {
				System.out.print(map[row][col]);
			}
			System.out.println();
		}
	}

	/**
	 * Get the GameMap as a row-major 2D char array.  With (0, 0) at top left, point (col x, row y) is at
	 * map[row y][col x]
	 *
	 * @return the GameMap as a row-major 2D char array
	 */
	@Override
	public char[][] getMapAsCharArray() {
		// TODO see about whether this can interfere with the original map.
		return map;
	}

	@Override
	public char[][] getSquareAreaAroundLocation(Coordinates coordinates, int distance) {
		// TODO implement
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public char[][] getRectangularAreaAroundLocation(Coordinates coordinates, int width, int height) {
		// TODO implement
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public char[][] getCircularAreaAroundLocation(Coordinates coordinates, int radius) {
		// TODO implement
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public char getTileAtLocation(Coordinates coordinates) {
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
}
