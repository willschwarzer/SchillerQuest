package game;

public abstract class Creature extends Entity {
	private Stats stats;

	/**
	 * Get an informative message about the Creature.
	 *
	 * @return An informative message about the Creature.
	 */
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

	public Creature(Coordinates coordinates, GameMap map, char graphic, int level) {
		super(coordinates, map, graphic);
		this.stats = new Stats(level);
	}

	/**
	 * Creates a creature at given coordinates with specified GameMap.
	 * @param coordinates
	 * @param map
	 */
	public Creature(Coordinates coordinates, GameMap map, char graphic) {super(coordinates, map, graphic);}

	/**
	 * Creates a entity at given coordinates
	 *
	 * @return Gives the stats object of this creature
	 */
	public Stats getStats() {
		return stats;
	}
}
