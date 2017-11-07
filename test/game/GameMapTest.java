package game;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

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

	@Disabled("testGetSquareAreaAroundLocation() not yet implemented")
	@Test
	void testGetSquareAreaAroundLocation() {
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
