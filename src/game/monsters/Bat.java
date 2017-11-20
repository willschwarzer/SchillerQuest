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
		Map<String, Item> equipped = new HashMap<>();
		equipped.put("weapon", new Item("weapon", level + 3, "Bat Claws"));
		equipped.put("armor", new Item("armor", level + 3, "Bat Wings "));
		setEquipped(equipped);
		setName("the level " + level + " bat");
	}

	@Override
	public String getInfo() {
		return "null";
	}

	@Override
	public char getMapGraphic() {
		return 'b';
	}
}
