package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Creature {
	private List<InventoryItem> backpack = new ArrayList<>();

	public Player(Coordinates coordinates) {
		super(coordinates);
	}

	//When first spawned
	public Player(Coordinates coordinates, Stats stats) {
		super(coordinates, stats);
		this.backpack.add(new InventoryItem("weapon", 1, "Basic stick"));
		this.backpack.add(new InventoryItem("weapon", 1, "better stick"));
		this.backpack.add(new InventoryItem("shoes", 1, "nike airmags"));
		this.backpack.add(new InventoryItem("weapon", 4, "Sharp Katana"));
		this.backpack.add(new InventoryItem("amulet", 5, "screw you f-rum"));
		this.backpack.add(new InventoryItem("shield", 6, "pride"));
		this.backpack.add(new InventoryItem("shoes", 4, "Crocs"));
		this.backpack.add(new InventoryItem("armor", 2, "Rotblatt shirt"));
		setName("Player");

//		HashMap<String, InventoryItem> equipped = new HashMap<>();
//		equipped.put("weapon", new InventoryItem("weapon", 1, "Test Armor"));
//		equipped.put("armor",new InventoryItem("armor", 1, "Test Armor"));
//		setEquipped(equipped);
	}

	public void setBackpack(List<InventoryItem> backpack) {
		this.backpack = backpack;
	}

	@Override
	public String getInfo() {
		throw new UnsupportedOperationException("getInfo() not yet supported for Player");
	}

	@Override
	public char getMapGraphic() {
		return '@';
	}

	public List<InventoryItem> getBackpack() {
		return backpack;
	}
}
