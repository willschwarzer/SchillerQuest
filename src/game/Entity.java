package game;

import java.util.List;

/**
 * Interface for entities that will exist in the game
 */
public abstract class Entity implements MapViewable {
	private Coordinates coordinates;
	private GameMap map;
	private boolean isOccupiable;
	private List<InventoryItem> inventory;

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

	/**
	 * Get an informative message about the Entity.
	 *
	 * @return An informative message about the Entity.
	 */
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
	 * Sets the coordinates of the entity given coordinates
	 *
	 * @param coordinates The location the entity will be set to
	 */
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public void setItemList(List<InventoryItem> inventory) {
		this.inventory = inventory;
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

	public void setGameMap(GameMap map) {
		this.map = map;
	}
}
