package game.monsters;

import game.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Goblin extends Monster {

    /**
     * Constructor for a Goblin with only a level parameter.  Internal Coordinates are set to 0,0 and assumed to be set
     * later.
     *
     * @param level
     */
    public Goblin(int level) {
        this(null, null, level);
    }

    /**
     * Constructor for a Goblin with only a random parameter. This parameter will be used to produce a rat of a random
     * level. Internal Coordinates are set to 0,0 and assumed to be set later.
     *
     * @param random
     */
    public Goblin(Random random) {
        this(random.nextInt(4) + 3);
    }

    public Goblin(Coordinates coordinates, int level) {
        this(coordinates, null, level);
    }

    /**
     * Constructor for a Goblin with coordiates, the game map and level. This will create a rat object at give cooridantes.
     * This will also give the rat their standard armor and weapon and name. The game map is necessary for movement
     *
     * @param coordinates The coordinates of the Goblin
     * @param map The game map
     * @param level The level of the rat in terms of difficulty
     */
    public Goblin(Coordinates coordinates, GameMap map, int level) {
        super(coordinates, map, level);
        Map<String, Item> equipped = new HashMap<>();
        equipped.put("weapon", new Item("weapon", level/2 + 3, "Goblin Club"));
        equipped.put("armor", new Item("armor",  5, "Goblin Armor "));
        equipped.put("armor", new Item("shield", 3, "Wooden Shield "));
        setEquipped(equipped);
        setName("the level " + level + " goblin");
    }

    /**
     * Returns information about the Goblin
     *
     * @return It returns null because nothing is implemented for this
     */
    @Override
    public String getInfo() {
        return "null";
    }

    /**
     * Returns the char value for the Goblin
     *
     * @return It returns hard coded char value
     */
    @Override
    public char getMapGraphic() {
        return 'G';
    }
}

