package game;

public interface ControllerInterface {
    void keyAction(int key);
    void openInventory();
    void openMainScreen();
    void openOptions();
    void makeMove(int[] move);
    void attack(Entity attacker, Entity attackee);
    void whatIsTile(Coordinates position);
    void pickUp();
    void drop(Item item);
}
