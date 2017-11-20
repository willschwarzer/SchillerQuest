package game;

/**
 * Container to simplify passing coordinate values.
 */
public class Coordinates {
	private int x;
	private int y;

	/**
	 * Creates a Coordinates from the given coordinates
	 *
	 * @param x X value for the Coordinates
	 * @param y Y value for the Coordinates
	 */
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;

		if (x < 0) {
			System.err.println(
					"Warning, x coordinate is below 0: " + x + ".  This almost certainly shouldn't happen.  Current "
					+ "stack trace:");
			new Throwable().printStackTrace(System.err);
		}
		if (y < 0) {
			System.err.println(
					"Warning, y coordinate is below 0: " + y + ".  This almost certainly shouldn't happen.  Current "
					+ "stack trace:");
			new Throwable().printStackTrace(System.err);
		}
	}

	/**
	 * Gets the X value of the Coordinates
	 *
	 * @return the X value of the Coordinates
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the Y value of the Coordinates
	 *
	 * @return the Y value of the Coordinates
	 */
	public int getY() {
		return y;
	}
}
