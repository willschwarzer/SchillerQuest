package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends Creature {
	private Stats stats;
	private List<InventoryItem> backpack = new ArrayList<>();
	private int exp;

	public Player(Coordinates coordinates) {
		super(coordinates);
	}

	//When first spawned
	public Player(Coordinates coordinates, Stats stats) {
		super(coordinates, stats);
		this.stats = stats;
//		this.backpack.add(new InventoryItem("weapon", 1, "Basic stick"));
//		this.backpack.add(new InventoryItem("weapon", 1, "better stick"));
//		this.backpack.add(new InventoryItem("shoes", 1, "nike airmags"));
//		this.backpack.add(new InventoryItem("weapon", 4, "Sharp Katana"));
//		this.backpack.add(new InventoryItem("amulet", 5, "screw you f-rum"));
//		this.backpack.add(new InventoryItem("shield", 6, "pride"));
//		this.backpack.add(new InventoryItem("shoes", 4, "Crocs"));
//		this.backpack.add(new InventoryItem("armor", 2, "Rotblatt shirt"));
		setName("Player");
		this.stats.setLevel(1);
		this.exp = 0;
	}

	public int getExp() {
		return exp;
	}

	public void gainExp(int level){
		this.exp += level * 25;
		if(this.exp >= 100) {
			stats.levelUp();
			this.exp = exp - 100;
		}
	}

	public void setBackpack(List<InventoryItem> backpack) {this.backpack = backpack;}

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
