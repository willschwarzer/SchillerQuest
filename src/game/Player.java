package game;

import java.util.HashMap;

public class Player extends Creature {
	private Stats stats;
	private HashMap inventory;

	public Player(Coordinates coordinates) {
		super(coordinates);
	}

	//When first spawned
	public Player(Coordinates coordinates, Stats stats) {
		super(coordinates, stats);
		this.stats = stats;
		this.inventory.put("weapon", new InventoryItem("weapon", 1, "Basic stick"));
		this.inventory.put("armor", new InventoryItem("armor", 1, "Your Wits"));
		this.inventory.put("shoes", new InventoryItem("weapon", 1, "Crocs"));
		setInventory(inventory);
	}

	public Player(Coordinates coordinates, Stats stats, HashMap inventory) {
		super(coordinates, stats);
		this.stats = stats;
		this.inventory = inventory;
		setInventory(inventory);

	}

	@Override
	public String getInfo() {
		throw new UnsupportedOperationException("getInfo() not yet supported for Player");
	}

	@Override
	public char getMapGraphic() {
		return '@';
		// TODO Change this hack to something better.
		//	throw new UnsupportedOperationException("getMapGraphic() not yet supported for Player");
	}
}
