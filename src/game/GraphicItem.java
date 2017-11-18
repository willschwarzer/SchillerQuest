package game;

public abstract class GraphicItem extends Entity implements MapViewable {

	/**
	 * Creates an item at given coordinates
	 *
	 * @param coordinates Coordinates the item will be created at
	 */
	public GraphicItem(Coordinates coordinates) {
		super(coordinates);
	}
}
