package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameMap implements GameMapInterface {
	private char[][] map;

	public GameMap(File file) {
		buildMapFromFile(file);
	}

	private boolean buildMapFromFile(File file) {
		int length = getFileLength(file);
		int width = getFileWidth(file);

		if (length == 0) {
			throw new IllegalArgumentException("Invalid input file - file length is 0");
			// TODO figure out how to handle this
		} else if (width == 0) {
			throw new IllegalArgumentException("Invalid input file - file width is 0");
			// TODO figure out how to handle this
		} else {
			map = new char[width][length];

			try {
				Scanner scanner = new Scanner(file);
				for (int i = 0; i <= length; i++) {
					String line = scanner.nextLine();
					for (int j = 0; j <= width; j++) {
					}
					scanner.nextLine();
				}
			} catch (FileNotFoundException e) {
				throw new IllegalArgumentException("File not found.", e);
			}

			// TODO FINISH THIS

		}
	}

	private int getFileLength(File file) {
		try {
			Scanner scanner = new Scanner(file);
			int length = 0;
			while (scanner.hasNextLine()) {
				length++;
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

	@Override
	public char[][] getMapAsCharArray() {
		// TODO implement
		throw new UnsupportedOperationException("Not yet implemented");
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
		// TODO implement
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
