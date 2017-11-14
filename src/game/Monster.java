package game;

public abstract class Monster extends Creature {
	private MonsterAI ai;

	public Monster(Coordinates coordinates, GameMap map) {
		super(coordinates, map);
	}

	public int[] getMove() {
		return ai.getMove();
	}
}
