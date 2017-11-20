package game;

import com.sun.org.apache.xpath.internal.operations.Bool;

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

	/**
	 * Adds experience to the player once they have killed something. If they have enough expericne they will level up.
	 *
	 * @param level The difficulty of the cuurent level which dictates the strength of the items
	 * @return Give the list of items created
	 */
	public boolean gainExp(int level) {
		boolean levelUp = false;
		exp += level * 10;
		if (exp >= 100 + (stats.getLevel()-1)*25) {
			exp = exp- 100 + (stats.getLevel()-1)*25;
			stats.levelUp();
			levelUp = true;
		}
		return levelUp;
	}

	/**
	 * Set the Backpack of the Player
	 *
	 * @param backpack The new backpack for the Entity
	 */
	public void setBackpack(List<Item> backpack) {
		this.backpack = backpack;
	}

	@Override
	public String getInfo() {
		throw new UnsupportedOperationException("getInfo() not yet supported for Player");
	}

	/**
	 * Returns the char value for the Knight.
	 *
	 * @return It returns hard coded char value
	 */
	@Override
	public char getMapGraphic() {
		return '@';
	}

	/**
	 * Gets the backpack of the player
	 *
	 * @return The backpack of the player
	 */
	public List<Item> getBackpack() {
		return backpack;
	}
}
