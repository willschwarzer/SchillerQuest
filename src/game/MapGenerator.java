package game;

import java.util.Random;

/**
 * Created by bruihlera on 11/15/17.
 */
public class MapGenerator {
    private Random random;
    private final int GRID_WIDTH = 5;
    private final int GRID_HEIGHT = 5;
    private final int NUM_ROOMS = 6;
    private Room[][] grid;

    public MapGenerator(long seed) {
        random = new Random(seed);
    }
    public GameMap generate(int difficulty) {
        GameMap map = new GameMap();


        return map;
    }

    private class GridItem {
        private int[] connections;
        private Tile[] tiles;
    }

    private class Room extends GridItem {
        private int difficulty;

        public Room(int difficulty, int[] connections) {
            this.difficulty = difficulty;
        }
    }

    private class Corridor extends GridItem {
        private Corridor(int[] connections) {

        }
    }
}
