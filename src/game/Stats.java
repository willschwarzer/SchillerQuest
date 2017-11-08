package game;

/**
 * Stats class that handles all of the values for creatures
 */
public class Stats {
    private int health;
    private int attack;
    private int speed;
    private int defense;
    private int vision;


    /**
     * Creates a Stats from the given coordinates
     *
     * @param health X value for the Coordinates
     * @param y Y value for the Coordinates
     */
    public Stats(int hp, int atk, int spd, int def, int vis) {
        this.health = hp;
        this.attack = atk;
        this.speed = spd;
        this.defense = def;
        this.vision = vis;
    }

    public int getHealth() {return health;}

    public int getAttack() {return attack;}

    public int getSpeed() {return speed;}

    public int getDefense() {return defense;}

    public int getVision() {return vision;}

    public int recieveAttack(int hit) {
        health -= hit;
        return health;

    }



}
