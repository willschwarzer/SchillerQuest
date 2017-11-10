package game;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for GameMap
 */
class GameMapTest {
	/**
	 * Tests whether a GameMap can be created properly from a proper input file and then output correctly as a 2D char
	 * array.
	 */
	@Test
	void testCreateMapFromFileAndGetAsCharArray() {
		// TODO have this check more generally (randomized each time?)
		File file = new File("test/resources/map.txt");
		GameMap gameMap = new GameMap(file);
		char[][] output = gameMap.getMapAsCharArray();

		char[] row0 = {'#', '#', '1', '#', '#', '#', '#', '#', '#', '#'};
		char[] row1 = {'#', ' ', '2', ' ', ' ', ' ', '@', ' ', ' ', '#'};
		char[] row2 = {'#', ' ', '3', '#', '#', '#', '#', '#', ' ', '#'};
		char[] row3 = {'#', ' ', '4', ' ', ' ', 'x', ' ', '#', ' ', '#'};
		char[] row4 = {'#', ' ', '5', '#', '#', ' ', '#', '#', ' ', '#'};
		char[] row5 = {'#', ' ', '6', ' ', ' ', ' ', ' ', ' ', ' ', '#'};
		char[] row6 = {'#', '#', '7', '#', '#', '#', '#', '#', '#', '#'};

		char[][] expected = {row0, row1, row2, row3, row4, row5, row6};

		assertArrayEquals(expected, output);
	}

	/**
	 * Tests whether getSquareAreaAroundLocation() works properly
	 */
	@Test
	void testGetSquareAreaAroundLocation() {
		File file = new File("test/resources/map.txt");
		GameMap gameMap = new GameMap(file);
		Tile[][] output = gameMap.getSquareAreaAroundLocation(new Coordinates(7, 2), 1);

		char[][] charOutput = new char[output.length][output[0].length];
		for (int row = 0; row < output.length; row++) {
			for (int col = 0; col < output[0].length; col++) {
				charOutput[row][col] = output[row][col].getMapGraphic();
			}
		}

		char[] row0 = {'@', ' ', ' '};
		char[] row1 = {'#', '#', ' '};
		char[] row2 = {' ', '#', ' '};

		char[][] expected = {row0, row1, row2};

		assertArrayEquals(expected, charOutput);
	}

	/**
	 * Tests whether getSquareAreaAroundLocation() works properly
	 */
	@Test
	void testGetSquareAreaAroundLocationAsCharArray() {
		File file = new File("test/resources/map.txt");
		GameMap gameMap = new GameMap(file);
		char[][] output = gameMap.getSquareAreaAroundLocationAsCharArray(new Coordinates(5, 3), 1);

		char[] row0 = {'#', '#', '#'};
		char[] row1 = {' ', 'x', ' '};
		char[] row2 = {'#', ' ', '#'};

		char[][] expected = {row0, row1, row2};

		assertArrayEquals(expected, output);
	}

	@Test
	void testGetSquareAreaAroundLocationOverlappingWorldEdge() {
		File file = new File("test/resources/map.txt");
		GameMap gameMap = new GameMap(file);
		Tile[][] output = gameMap.getSquareAreaAroundLocation(new Coordinates(0, 2), 1);

		System.out.println("output.length: " + output.length + ", output[0].length: " + output[0].length);
		for (Tile[] row : output) {
			for (Tile tile : row) {
				try {
					System.out.print(tile.getMapGraphic());
				} catch (NullPointerException e) {
					System.out.print('␀');
				}
			}
			System.out.println();
		}

		char[][] charOutput = new char[output.length][output[0].length];
		for (int row = 0; row < output.length; row++) {
			for (int col = 0; col < output[0].length; col++) {
				charOutput[row][col] = output[row][col].getMapGraphic();
			}
		}

		char[] row0 = {' ', ' ', ' '};
		char[] row1 = {'#', '1', '#'};
		char[] row2 = {' ', '2', ' '};

		char[][] expected = {row0, row1, row2};

		assertArrayEquals(expected, charOutput);
	}

	/**
	 * Tests whether getSquareAreaAroundLocation() works properly
	 */
	@Test
	void testGetSquareAreaAroundLocationAsCharArrayWithOutOfWorldCoordinates() {
		File file = new File("test/resources/map.txt");
		GameMap gameMap = new GameMap(file);
		char[][] output = gameMap.getSquareAreaAroundLocationAsCharArray(new Coordinates(-1, 2), 1);

		for (char[] row : output) {
			System.out.println(Arrays.toString(row));
		}

		char[] row0 = {' ', ' ', ' '};
		char[] row1 = {' ', ' ', ' '};
		char[] row2 = {'#', '1', '#'};

		char[][] expected = {row0, row1, row2};

		assertArrayEquals(expected, output);
	}

	@Test
	void testGetSquareAreaAroundLocationWithOutOfWorldCoordinates() {
		File file = new File("test/resources/map.txt");
		GameMap gameMap = new GameMap(file);
		Tile[][] output = gameMap.getSquareAreaAroundLocation(new Coordinates(-1, 2), 1);

		for (Tile[] row : output) {
			for (Tile tile : row) {
				try {
					System.out.print(tile.getMapGraphic());
				} catch (NullPointerException e) {
					System.out.print('␀');
				}
			}
			System.out.println();
		}

		char[][] charOutput = new char[output.length][output[0].length];
		for (int row = 0; row < output.length; row++) {
			for (int col = 0; col < output[0].length; col++) {
				try {
					charOutput[row][col] = output[row][col].getMapGraphic();
				} catch (NullPointerException e) {
					charOutput[row][col] = ' ';
				}
			}
		}

		char[] row0 = {' ', ' ', ' '};
		char[] row1 = {' ', ' ', ' '};
		char[] row2 = {'#', '1', '#'};

		char[][] expected = {row0, row1, row2};

		assertArrayEquals(expected, charOutput);
	}

	@Disabled("testGetRectangularAreaAroundLocation() not yet implemented")
	@Test
	void testGetRectangularAreaAroundLocation() {
	}

	@Disabled("testGetCircularAreaAroundLocation() not yet implemented")
	@Test
	void testGetCircularAreaAroundLocation() {
	}

	@Disabled("testGetTileAtLocation() not yet implemented")
	@Test
	void testGetTileAtLocation() {
	}

}
