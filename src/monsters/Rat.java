package monsters;

import game.*;

import java.util.HashMap;
import java.util.Map;

public class Rat extends Monster {
    private Coordinates coordinates;
    private GameMap map;
    private Stats stats;
    private HashMap<String, InventoryItem> inventory = new HashMap<>();

    public Rat(Coordinates coordinates, GameMap map, int level) {
        super(coordinates, map, level);
        this.stats = new Stats(level);
        setMapGraphic('r');
        this.inventory.put("weapon", new InventoryItem("weapon", 1, "Rat Teeth"));
        this.inventory.put("armor", new InventoryItem("armor", 1, "Rat Skin"));
        setInventory(inventory);

    }

    public String getInfo(){

        return "null";
    }
}
