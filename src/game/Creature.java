package game;

public abstract class Creature extends Entity {
	Stats stats;

	abstract public String getInfo();

	public Creature(Coordinates coordinates) {
		super(coordinates);
	}

	public Stats getStats() {
		return stats;
	}
}
