package game;

/**
 * Interface for the world map.
 */
public interface GameMapInterface {
	/**
	 * Gets the current view of the entire map as a 2D char array
	 *
	 * @return the current view of the entire map as a 2D char array
	 */
	char[][] getMapAsCharArray();

	/**
	 * Gets the portion of the map in a square around a position.
	 *
	 * @param coordinates Location to get the map around
	 * @param distance    Distance away from the location to include
	 * @return The specified portion of the map as a 2D array
	 */
	Tile[][] getSquareAreaAroundLocation(Coordinates coordinates, int distance);

	/**
	 * Gets the portion of the map in a rectangle around a position.
	 *
	 * @param coordinates Location to get the map around.
	 * @param width       Distance away from the location in the X direction to include
	 * @param height      Distance away from the location in the Y direction to include
	 * @return The specified portion of the map as a 2D array
	 */
	Tile[][] getRectangularAreaAroundLocation(Coordinates coordinates, int width, int height);

	/**
	 * Gets the portion of the map in a circle around a position.
	 *
	 * @param coordinates Location to get the map around
	 * @param radius      Radius away from the location to include
	 * @return The specified portion of the map as a 2D array
	 */
	Tile[][] getCircularAreaAroundLocation(Coordinates coordinates, int radius);

	/**
	 * Gets the map at a specified coordinate
	 *
	 * @param coordinates Location to get the map at
	 * @return The point of the map
	 */
	Tile getTileAtLocation(Coordinates coordinates);
}
