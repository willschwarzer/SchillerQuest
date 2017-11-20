package game;

import java.util.List;

/**
 * Interface for entities that will exist in the game
 */
public abstract class Entity implements MapViewable {
	private Coordinates coordinates;
	private GameMap map;
	private List<Item> inventory;

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


	/**
	 * Set the Inventory of the Entity
	 *
	 * @param inventory The new Inventory for the Entity
	 */
	public void setItemList(List<Item> inventory) {
		this.inventory = inventory;
	}

	/**
	 * Gets the GameMap of the Entity
	 *
	 * @return The GameMap of the Entity
	 */
	public GameMap getGameMap() {
		return map;
	}

	public void setGameMap(GameMap map) {
		this.map = map;
	}
}
