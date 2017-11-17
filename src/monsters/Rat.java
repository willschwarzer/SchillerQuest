package monsters;


import game.Coordinates;
import game.GameMap;
import game.Monster;
import game.Stats;

public class Rat extends Monster {
    private Coordinates coordinates;
    private GameMap map;
    private boolean isOccupiable;
    private char graphic;
    private Stats stats;

    public Rat(Coordinates coordinates, GameMap map, char graphic, int level) {
        super(coordinates, map, 'r', level);
        this.graphic = 'R' ;
        this.stats = new Stats(level);
    }

    public String getInfo(){

        return "null";
    }

    public char getGraphic(){
        return graphic;
    }
}
