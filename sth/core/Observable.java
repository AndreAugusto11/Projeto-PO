package sth.core;

public interface Observable {
	void attach(Observer observer);
	void dettach(Observer observer);
	void notifyObservers(String discName, String projName);
	State getState();
}
