package monsters;


import game.Coordinates;
import game.GameMap;
import game.Monster;
import game.Stats;

public class Rat extends Monster {
    private Coordinates coordinates;
    private GameMap map;
    private Stats stats;

    public Rat(Coordinates coordinates, GameMap map, int level) {
        super(coordinates, map, level);
        this.stats = new Stats(level);
        setMapGraphic('r');
    }

    public String getInfo(){

        return "null";
    }
}
