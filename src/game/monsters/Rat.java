package game.monsters;

import game.*;

import java.util.HashMap;
import java.util.Map;

public class Rat extends Monster {
	/**
	 * Constructor for a Rat with only a level parameter.  Internal Coordinates are set to 0,0 and assumed to be set
	 * later.
	 *
	 * @param level
	 */
	public Rat(int level) {
		super(new Coordinates(0, 0), level);
	}

	public Rat(Coordinates coordinates, int level) {
		super(coordinates, level);
		Map<String, InventoryItem> equipped = new HashMap<>();
		equipped.put("weapon", new InventoryItem("weapon", 1, "Rat Teeth"));
		equipped.put("armor", new InventoryItem("armor", 1, "Rat Skin"));
		setEquipped(equipped);
	}

	public Rat(Coordinates coordinates, GameMap map, int level) {
		super(coordinates, map, level);
		Map<String, InventoryItem> equipped = new HashMap<>();
		equipped.put("weapon", new InventoryItem("weapon", 1, "Rat Teeth"));
		equipped.put("armor", new InventoryItem("armor", 1, "Rat Skin"));
		setEquipped(equipped);
	}

	public String getInfo() {
		return "null";
	}

	@Override
	public char getMapGraphic() {
		return 'r';
	}
}
