package game.monsters;

import game.*;

import java.util.HashMap;
import java.util.Map;

public class Rat extends Monster {
	private Coordinates coordinates;
	private GameMap map;

	public Rat(Coordinates coordinates, GameMap map, int level) {
		super(coordinates, map, level);
		Stats stats = new Stats(level);
		setStats(stats);
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
