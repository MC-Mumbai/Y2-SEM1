package edu.curtin.oose2024s1.assignment2.o_f;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(String message);
}