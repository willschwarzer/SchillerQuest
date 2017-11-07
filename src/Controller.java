public class Controller implements ControllerInterface {
    public void keyAction(int key) {
        System.out.println("You pressed " + key);
    }

    public void openInventory() {
        System.out.println("This is your inventory!");
    }

    public void openMainScreen(){

    }

    public void openOptions(){

    }

    public void makeMove(int[] move) {

    }

    public void attack(Entity attacker, Entity attackee) {

    }

    public void whatIsTile(Coordinates position) {

    }

    public void pickUp() {

    }

    public void drop(Item item) {

    }
}
