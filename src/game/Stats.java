package game;

/**
 * Stats class that handles all of the values for creatures
 */
public class Stats {
	private int health;
	private int attack;
	private int speed;
	private int defense;
	private int vision;


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

	public int getHealth() {
		return health;
	}

	public int getAttack() {
		return attack;
	}

	public int getSpeed() {
		return speed;
	}

	public int getDefense() {
		return defense;
	}

	public int getVision() {
		return vision;
	}

	public int recieveAttack(int hit) {
		health -= hit;
		return health;
	}


}
