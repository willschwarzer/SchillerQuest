package game;

public interface Subject {
	boolean addObserver(Observer o);

	boolean removeObserver(Observer o);

	void notifyObservers();
}
