package game;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for GameMap.
 */
class GameMapTest {
	@Test
	void testGetSquareAreaAroundLocationAllInWorld() {
		File file = new File("test/resources/map.txt");
		GameMap gameMap = new GameMap(file);
		Tile[][] output = gameMap.getSquareAreaAroundLocation(new Coordinates(7, 2), 1);
		Tile.markTileVisiblity(output, true);
		char[][] charOutput = GameMap.convertTileArrayToCharArray(output);

		char[] row0 = {'@', ' ', ' '};
		char[] row1 = {'#', '#', ' '};
		char[] row2 = {' ', '#', ' '};
		char[][] expected = {row0, row1, row2};

		assertArrayEquals(expected, charOutput);
	}

	@Test
	void testGetSquareAreaAroundLocationIncludingOutsideWorld() {
		File file = new File("test/resources/map.txt");
		GameMap gameMap = new GameMap(file);
		Tile[][] output = gameMap.getSquareAreaAroundLocation(new Coordinates(2, 0), 1);
		Tile.markTileVisiblity(output, true);
		char[][] charOutput = GameMap.convertTileArrayToCharArray(output);
		char out = Terrain.getOutOfWorldTerrainGraphic();

		char[] row0 = {out, out, out};
		char[] row1 = {'#', '1', '#'};
		char[] row2 = {' ', '2', ' '};
		char[][] expected = {row0, row1, row2};

		assertArrayEquals(expected, charOutput);
	}

	@Test
	void testGetSquareAreaIncludingOutsideWorld() {
		File file = new File("test/resources/alphabet.txt");

		GameMap gameMap = new GameMap(file);
		Tile[][] output = gameMap.getSquareAreaAroundLocation(new Coordinates(3, 0), 1);
		Tile.markTileVisiblity(output, true);
		char[][] charOutput = GameMap.convertTileArrayToCharArray(output);
		char out = Terrain.getOutOfWorldTerrainGraphic();

		char[] row0 = {out, out, out};
		char[] row1 = {'B', 'b', 'C'};
		char[] row2 = {'H', 'h', 'I'};
		char[][] expected = {row0, row1, row2};

		assertArrayEquals(expected, charOutput);
	}

	@Test
	void testGetSquareAreaAroundLocationAllInWorldAlphabet() {
		File file = new File("test/resources/alphabet.txt");

		GameMap gameMap = new GameMap(file);
		Tile[][] output = gameMap.getSquareAreaAroundLocation(new Coordinates(3, 1), 1);
		Tile.markTileVisiblity(output, true);
		char[][] charOutput = GameMap.convertTileArrayToCharArray(output);

		char[] row0 = {'B', 'b', 'C'};
		char[] row1 = {'H', 'h', 'I'};
		char[] row2 = {'N', 'n', 'O'};
		char[][] expected = {row0, row1, row2};

		assertArrayEquals(expected, charOutput);
	}


	/**
	 * Prints out a row-major 2D char array to the console, useful for troubleshooting tests.
	 *
	 * @param array Row-major 2D char array
	 */
	private static void printCharArrayToConsole(char[][] array) {
		for (int row = 0; row < array.length; row++) {
			for (int col = 0; col < array[0].length; col++) {
				System.out.print(array[row][col]);
			}
			System.out.println();
		}
	}
}
