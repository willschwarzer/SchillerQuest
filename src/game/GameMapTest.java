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