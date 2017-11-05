package game;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for GameMap
 */
class GameMapTest {
	@Test
	void testGetMapAsCharArray() {
		// TODO have this check more generally (randomized each time?)
		File file = new File("src/game/map.txt");
		GameMap gameMap = new GameMap(file);
		char[][] output = gameMap.getMapAsCharArray();

		// NOTE that that the graphical representation is flipped diagonally (top right to bottom left, etc.), because
		// it's stored column-major
		char[] col0 = {'#', '#', '#', '#', '#', '#', '#'};
		char[] col1 = {'#', ' ', ' ', ' ', ' ', ' ', '#'};
		char[] col2 = {'1', '2', '3', '4', '5', '6', '7'};
		char[] col3 = {'#', ' ', '#', ' ', '#', ' ', '#'};
		char[] col4 = {'#', ' ', '#', ' ', '#', ' ', '#'};
		char[] col5 = {'#', ' ', '#', 'x', ' ', ' ', '#'};
		char[] col6 = {'#', '@', '#', ' ', '#', ' ', '#'};
		char[] col7 = {'#', ' ', '#', '#', '#', ' ', '#'};
		char[] col8 = {'#', ' ', ' ', ' ', ' ', ' ', '#'};
		char[] col9 = {'#', '#', '#', '#', '#', '#', '#'};

		char[][] expected = {col0, col1, col2, col3, col4, col5, col6, col7, col8, col9};

		assertArrayEquals(expected, output);
	}

	@Disabled("Not yet implemented")
	@Test
	void getSquareAreaAroundLocation() {
	}

	@Disabled("Not yet implemented")
	@Test
	void getRectangularAreaAroundLocation() {
	}

	@Disabled("Not yet implemented")
	@Test
	void getCircularAreaAroundLocation() {
	}

	@Disabled("Not yet implemented")
	@Test
	void getTileAtLocation() {
	}

}