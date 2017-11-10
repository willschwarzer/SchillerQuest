package game;


public abstract class Item extends Entity implements MapViewable {

	/**
	 * Creates an item at given coordinates
	 *
	 * @param coordinates Coordinates the item will be created at
	 */
	public Item(Coordinates coordinates) {
		super(coordinates);
	}

}
