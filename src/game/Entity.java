package game;

/**
 * Interface for entities that will exist in the game
 */
public abstract class Entity implements MapViewable {
	private Coordinates coordinates;
	private GameMap map;
	private boolean isOccupiable;
	private char graphic;

	/**
	 * Creates an Entity at given coordinates
	 *
	 * @param coordinates Coordinates the entity will be created at
	 */
	public Entity(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Creates an Entity at given coordinates with specified GameMap.
	 *
	 * @param coordinates
	 * @param map
	 */
	public Entity(Coordinates coordinates, GameMap map) {
		this.coordinates = coordinates;
		this.map = map;
	}

	abstract public String getInfo();

	/**
	 * Gets the Coordinates of the Entity
	 *
	 * @return The Coordinates of the Entity
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * Give the char value that represents this entity graphically
	 *
	 * @return Returns the char that represents this entity graphically
	 */
	public char getMapGraphic() {
		return graphic;
	}

	/**
	 * Sets the coordinates of the entity given coordinates
	 *
	 * @param coor The location the entity will be set to
	 */
	public void setCoordinates(Coordinates coor) {
		coordinates = coor;
	}

	/**
	 * Check whether another Entity can occupy the same space as this Entity
	 *
	 * @return Returns if this Entity allows another Entity to exist in the same location
	 */
	public boolean isOccupiable() {
		return isOccupiable;
	}

	public GameMap getGameMap() {
		return map;
	}
}
