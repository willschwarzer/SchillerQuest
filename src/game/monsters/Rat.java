package game.monsters;

import game.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Rat extends Monster {
	/**
	 * Constructor for a Rat with only a level parameter.  Internal Coordinates are set to 0,0 and assumed to be set
	 * later.
	 *
	 * @param level
	 */
	public Rat(int level) {
		this(null, null, level);
	}

	public Rat(Random random) {
		this(random.nextInt(4)+1);
	}

	public Rat(Coordinates coordinates, int level) {
		this(coordinates, null, level);
	}

	public Rat(Coordinates coordinates, GameMap map, int level) {
		super(coordinates, map, level);
		Map<String, InventoryItem> equipped = new HashMap<>();
		equipped.put("weapon", new InventoryItem("weapon", 1, "Rat Teeth"));
		equipped.put("armor", new InventoryItem("armor", 1, "Rat Skin"));
		setEquipped(equipped);
		setName("the level " + level + " rat");
	}

	public String getInfo() {
		return "null";
	}

	@Override
	public char getMapGraphic() {
		return 'r';
	}
}
