package monsters;

import game.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rat extends Monster {
    private Coordinates coordinates;
    private GameMap map;

    public Rat(Coordinates coordinates, GameMap map, int level) {
        super(coordinates, map, level);
        Stats stats = new Stats(level);
        setStats(stats);
        setMapGraphic('r');
        HashMap<String, InventoryItem> equipped = new HashMap<>();
        equipped.put("weapon", new InventoryItem("weapon", level, "Rat Teeth"));
        equipped.put("armor",new InventoryItem("armor", level, "Rat Skin"));
        setEquipped(equipped);
        setName("Lvl " + level + " Rat");

    }

    public String getInfo(){

        return "null";
    }
}
