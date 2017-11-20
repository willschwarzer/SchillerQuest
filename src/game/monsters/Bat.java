package game.monsters;

import game.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bat extends Monster {

	public Bat(int level) {
		this(null, null, level);
	}

	public Bat(Random random) {
		this(random.nextInt(4) + 3);
	}

	public Bat(Coordinates coordinates, int level) {
		this(coordinates, null, level);
	}

	public Bat(Coordinates coordinates, GameMap map, int level) {
		super(coordinates, map, level);
		Map<String, InventoryItem> equipped = new HashMap<>();
		equipped.put("weapon", new InventoryItem("weapon", level, "Bat Claws"));
		equipped.put("armor", new InventoryItem("armor", level, "Bat Wings "));
		setEquipped(equipped);
		setName("the level " + level + " bat");
	}

	public String getInfo() {
		return "null";
	}

	@Override
	public char getMapGraphic() {
		return 'b';
	}
}
