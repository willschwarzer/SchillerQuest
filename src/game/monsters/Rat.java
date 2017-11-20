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

	/**
	 * Constructor for a Rat with only a random parameter. This parameter will be used to produce a rat of a random
	 * level. Internal Coordinates are set to 0,0 and assumed to be set later.
	 *
	 * @param random
	 */
	public Rat(Random random) {
		this(random.nextInt(4) + 1);
	}

	public Rat(Coordinates coordinates, int level) {
		this(coordinates, null, level);
	}

	/**
	 * Constructor for a Rat with coordiates, the game map and level. This will create a rat object at give cooridantes.
	 * This will also give the rat their standard armor and weapon and name. The game map is necessary for movement
	 *
	 * @param coordinates The coordinates of the rat
	 * @param map The game map
	 * @param level The level of the rat in terms of difficulty
	 */
	public Rat(Coordinates coordinates, GameMap map, int level) {
		super(coordinates, map, level);
		Map<String, Item> equipped = new HashMap<>();
		equipped.put("weapon", new Item("weapon", 1, "Rat Teeth"));
		equipped.put("armor", new Item("armor", 1, "Rat Skin"));
		setEquipped(equipped);
		setName("the level " + level + " rat");
	}

	/**
	 * Returns information about the rat.
	 *
	 * @return It returns null because nothing is implemented for this
	 */
	@Override
	public String getInfo() {
		return "null";
	}

	/**
	 * Returns the char value for the rat.
	 *
	 * @return It returns hard coded char value
	 */
	@Override
	public char getMapGraphic() {
		return 'r';
	}
}
