package game;

import java.util.ArrayList;
import java.util.List;

public class Player extends Creature {
	private Stats stats;
	private List<InventoryItem> backpack = new ArrayList<>();

	public Player(Coordinates coordinates) {
		super(coordinates);
	}

	//When first spawned
	public Player(Coordinates coordinates, Stats stats) {
		super(coordinates, stats);
		this.stats = stats;
		this.backpack.add(new InventoryItem("weapon", 1, "Basic stick"));
		this.backpack.add(new InventoryItem("weapon", 1, "better stick"));
		this.backpack.add(new InventoryItem("shoes", 1, "nike airmags"));
		this.backpack.add(new InventoryItem("weapon", 1, "Basic stick"));
		this.backpack.add(new InventoryItem("amulet", 1, "screw you f-rum"));
		this.backpack.add(new InventoryItem("shield", 1, "pride"));
		this.backpack.add(new InventoryItem("shoes", 1, "Crocs"));
		this.backpack.add(new InventoryItem("armor", 1, "Rotblatt shirt"));
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

	public List<InventoryItem> getBackpack() {
		return backpack;
	}
}
