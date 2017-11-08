package game;

public class Game {
    public void main(String[] args) {
        ControllerInterface controller = new Controller();
        View view = new View();
        GameMap map = new GameMap("level1.txt");

        controller.setView(view);
        controller.setGameMap(map);
    }
}
