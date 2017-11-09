package game;

/**
 * Created by higgsd on 11/8/17.
 */
public interface Subject {
    void addObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();

}
