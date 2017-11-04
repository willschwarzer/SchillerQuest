package game;

/**
 * Container to simplify passing coordinate values.
 */
public class Coordinates {
	private int x;
	private int y;

	/**
	 * Creates a Coordinates from the given coordinates
	 * @param x X value for the Coordinates
	 * @param y Y value for the Coordinates
	 */
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the X value of the Coordinates
	 * @return the X value of the Coordinates
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the Y value of the Coordinates
	 * @return the Y value of the Coordinates
	 */
	public int getY() {
		return y;
	}
}
