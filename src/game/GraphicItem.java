package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GraphicItem extends Entity implements MapViewable {

	/**
	 * Creates an item at given coordinates
	 *
	 * @param coordinates Coordinates the item will be created at
	 */
	public GraphicItem(Coordinates coordinates) {
		super(coordinates);
	}

	public static List<GraphicItem> getAppropriateItems(Random random, int difficulty) {
		// TODO improve this
		List<GraphicItem> items = new ArrayList<>();

		for (int i = 0; i < difficulty; i++) {
			items.add(createItem(random, difficulty));
		}

		return items;
	}

	public static GraphicItem createItem(Random random, int difficulty) {
		// TODO make this actually work and return a random Monster
		return new Flag();
	}
}
