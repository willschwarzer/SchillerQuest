package game;

import java.util.Observable;

public class GameModel extends Observable {
    GameMap map;

    public void moveCreature(Creature creature, int[] direction) {
        
    }

    public void setGameMap(GameMap map) {
        this.map = map;
    }

    public Player getPlayer() {return map.getPlayer();}
}