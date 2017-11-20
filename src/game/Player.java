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

		// Starting kit
		this.backpack.add(new Item("weapon", 1, "Sharpened Rock"));
		this.backpack.add(new Item("shoes", 1, "Crocs"));
		this.backpack.add(new Item("armor", 1, "Rotblatt shirt"));

		this.stats = stats;

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
