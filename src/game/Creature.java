package game;

import java.util.HashMap;
import java.util.Map;

public abstract class Creature extends Entity {
	private Stats stats;
	private Map<String, Item> equipped = new HashMap<>();
	private String name;
	private int level;

	/**
	 * Get an informative message about the Creature.
	 *
	 * @return An informative message about the Creature.
	 */
	@Override
	abstract public String getInfo();

	/**
	 * Creates a creature at given coordinates with the default Stats.  Using the Creature constructor with Stats is
	 * preferable.
	 *
	 * @param coordinates Coordinates the creature will be created at
	 * @see #Creature(Coordinates, Stats)
	 */
	public Creature(Coordinates coordinates) {
		super(coordinates);
		this.stats = new Stats();
	}

	/**
	 * Creates a creature at given coordinates with the given Stats
	 *
	 * @param coordinates Coordinates the creature will be created at
	 * @param stats       Stats the creature will be created with.
	 */
	public Creature(Coordinates coordinates, Stats stats) {
		super(coordinates);
		this.stats = stats;
	}

	/**
	 * Creates a Creature at the given coordinates with Stats appropriate for the level
	 *
	 * @param coordinates Coordinates of the Creature
	 * @param level       Level of the Creature to derive Stats from
	 */
	public Creature(Coordinates coordinates, int level) {
		super(coordinates);
		this.level = level;
		this.stats = new Stats(level);
	}

	public Creature(Coordinates coordinates, GameMap map, int level) {
		super(coordinates, map);
		this.level = level;
		this.stats = new Stats(level);
	}

	/**
	 * Creates a creature at given coordinates with specified GameMap.
	 *
	 * @param coordinates
	 * @param map
	 */
	public Creature(Coordinates coordinates, GameMap map) {
		super(coordinates, map);
	}

	/**
	 * Creates a entity at given coordinates
	 *
	 * @return Gives the stats object of this creature
	 */
	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public Map<String, Item> getEquipped() {
		return equipped;
	}

	public void setEquipped(Map<String, Item> equipped) {
		this.equipped = equipped;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getHealth() { return getStats().getHealth(); }
}
