package game;

public abstract class Entity implements MapViewable {
    Coordinates coordinates;
    boolean occupyable;
    char graphic;

    abstract public String getInfo();

    public Coordinates getCoordinates() {
        return coordinates;
    }

   public char getMapGraphic() {
        return graphic;
    }

   public void setCoordinates(Coordinates coor) {

        coordinates = coor;
   }

   public boolean isOccupyable() {
        return occupyable;
    }


}
