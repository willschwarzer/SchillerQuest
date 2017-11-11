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
	 * Creates a creature at given coordinates
	 *
	 * @param coordinates Coordinates the creature will be created at
	 */
	public Creature(Coordinates coordinates) {
		super(coordinates);
	}

	/**
	 * Creates a entity at given coordinates
	 *
	 * @return Gives the stats object of this creature
	 */
	public Stats getStats() {
		return stats;
	}
}