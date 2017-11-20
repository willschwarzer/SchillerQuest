package game;

/**
 * Stats class that handles all of the values for Creatures
 */
public class Stats {
	private int health;
	private int attack;
	private int speed;
	private int defense;
	private int vision;
	private int level;

	/**
	 * Default Stats constructor, sets all stats to 5.  Avoid using - only included to make general coding easier and
	 * will log usages to System.err
	 */
	public Stats() {
		health = 5;
		attack = 5;
		speed = 5;
		defense = 5;
		vision = 5;

		System.err.println("Warning, used parameter-less Stats constructor, try to avoid.");
	}

	/**
	 * Creates a Stats from the given values.
	 *
	 * @param health  Health value for the Stats
	 * @param attack  Attack value for the Stats
	 * @param speed   Speed value for the Stats
	 * @param defense Defense value for the Stats
	 * @param vision  Vision value for the Stats
	 */
	public Stats(int health, int attack, int speed, int defense, int vision) {
		this.health = health;
		this.attack = attack;
		this.speed = speed;
		this.defense = defense;
		this.vision = vision;
	}

	public Stats(int level) {
		this.health = randomWithRange(3 * level, 10 * level);
		this.attack = randomWithRange(level, 2 * level);
		this.speed = randomWithRange(level, 2 * level);
		this.defense = randomWithRange(level, 2 * level);
		this.vision = 2;
		this.level = level;
	}

	private int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getVision() {
		return vision;
	}

	public void setVision(int vision) {
		this.vision = vision;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void levelUp() {
		this.level += 1;
		this.health += 2;
		this.attack += 2;
		this.speed += 2;
		this.defense += 2;
		System.out.println("levelUp: " + level);
	}
}
