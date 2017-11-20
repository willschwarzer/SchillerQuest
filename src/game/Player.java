package game;

import java.util.ArrayList;
import java.util.List;

public class Player extends Creature {
	private Stats stats;
	private int exp;
	private List<Item> backpack = new ArrayList<>();
	static final int MAX_BACKPACK_SIZE = 10;

	public Player(Coordinates coordinates) {
		super(coordinates);
	}

	public Player(Coordinates coordinates, Stats stats) {
		super(coordinates, stats);
		this.stats = stats;

		this.backpack.add(new Item("weapon", 1, "Basic stick"));
		this.backpack.add(new Item("weapon", 1, "better stick"));
		this.backpack.add(new Item("shoes", 1, "nike airmags"));
		this.backpack.add(new Item("weapon", 4, "Sharp Katana"));
		this.backpack.add(new Item("amulet", 5, "screw you f-rum"));
		this.backpack.add(new Item("shield", 6, "pride"));
		this.backpack.add(new Item("shoes", 4, "Crocs"));
		this.backpack.add(new Item("armor", 2, "Rotblatt shirt"));

		setName("Player");
		this.stats.setLevel(1);
		this.exp = 0;
	}

	public int getExp() {
		return exp;
	}

	public void gainExp(int level) {
		exp += level * 25;
		if (exp >= 100) {
			stats.levelUp();
			exp -= 100;
		}
	}

	public void setBackpack(List<Item> backpack) {
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

	public List<Item> getBackpack() {
		return backpack;
	}
}
