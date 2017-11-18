package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Monster extends Creature {
	public Monster(Coordinates coordinates) {
		super(coordinates);
	}

	/**
	 * Gets a List of Monsters appropriate for the given difficulty using the given Random.
	 * @param random Random to use in Monster generation.
	 * @param difficulty Difficulty to generate with.
	 * @return The generated Monsters for the given difficulty.
	 */
	public static List<Monster> getAppropriateMonsters(Random random, int difficulty) {
		// TODO improve this
		List<Monster> monsters = new ArrayList<>();

		for (int i = 0; i < difficulty; i++) {
			monsters.add(createMonster(random, difficulty));
		}

		return monsters;
	}

	/**
	 * Creates an individual Monster for the given difficulty using the given Random.
 	 * @param random Random to use in Monster generation.
	 * @param difficulty Difficulty to generate with.
	 * @return The generated Monster for the given difficulty.
	 */
	private static Monster createMonster(Random random, int difficulty) {
		// TODO make this actually work and return a random Monster
		return new Zombie();
	}
}
