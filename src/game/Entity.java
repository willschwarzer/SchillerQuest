package game;
/**
 * Interface for entities that will exist in the game
 */
public abstract class Entity implements MapViewable {
	Coordinates coordinates;
	boolean isOccupiable;
	char graphic;

	abstract public String getInfo();

	/**
	 * Creates a entity at given coordinates
	 *
	 * @param coordinates Coordinates the entity will be created at
	 */
	public Entity(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	/**
	 * Creates a entity at given coordinates
	 *
	 * @return Give location of the entity in coordinates
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * Give the char value that represents this entity
	 *
	 * @return Returns the char that represents this entity
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
	 * Returns if the another entity can occupy the same space as this entity
	 *
	 * @return Returns boolean if this entity allows another entity to exist in the same location
	 */
	public boolean isOccupiable() {
		return isOccupiable;
	}
}
