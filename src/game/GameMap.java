package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameMap implements GameMapInterface {
	/**
	 * Stores the map tiles in a column-major 2D array.  With (0, 0) at top left, point (x, y) is at map[x][y]
	 */
	private char[][] map;

	public GameMap(File file) {
		buildMapFromFile(file);
	}

	private boolean buildMapFromFile(File file) {
		int length = getFileLength(file);
		int width = getFileWidth(file);

		if (length == 0) {
			throw new IllegalArgumentException("Invalid input file - length is 0 (file " + file);
			// TODO figure out how to handle this
		} else if (width == 0) {
			throw new IllegalArgumentException("Invalid input file - width is 0 (file " + file);
			// TODO figure out how to handle this
		} else {
			map = new char[width][length];

			try {
				Scanner scanner = new Scanner(file);
				for (int y = 0; y < length; y++) {
					String currentLine = scanner.nextLine();
					if (currentLine.length() != width) {
						throw new IllegalArgumentException(
								"Line " + y + " in file " + file + "has incorrect width (expected " + width + " " +
								"but was " + currentLine.length() + ")");
					} else {
						for (int x = 0; x < currentLine.length(); x++) {
							map[x][y] = currentLine.charAt(x);
						}
					}
				}
				return true;
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("File not found.", e);
			}
		}
	}

	private int getFileLength(File file) {
		try {
			Scanner scanner = new Scanner(file);
			int length = 0;
			while (scanner.hasNextLine()) {
				length++;
				scanner.nextLine();
			}
			scanner.close();
			return length;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.", e);
		}
	}

	private int getFileWidth(File file) {
		try {
			Scanner scanner = new Scanner(file);
			int width = scanner.nextLine().length();
			scanner.close();
			return width;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("File not found.", e);
		}
	}

	public void printMapToConsole() {
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				System.out.print(map[x][y]);
			}
			System.out.println();
		}
	}

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
		if (coordinates.getX() > map.length) {
			// TODO figure out how to handle
			throw new IllegalArgumentException("X coordinate too large");
		}
		if (coordinates.getY() > map[0].length) {
			// TODO figure out how to handle
			throw new IllegalArgumentException("Y coordinate too large");
		}
		return map[coordinates.getX()][coordinates.getY()];
	}
}
