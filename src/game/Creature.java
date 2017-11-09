package game;

public abstract class Creature extends Entity {
	Stats stats;

	abstract public String getInfo();

	public Stats getStats() {
		return stats;
	}
}
