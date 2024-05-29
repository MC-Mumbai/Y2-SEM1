package edu.curtin.oose2024s1.assignment2.states;

import edu.curtin.oose2024s1.assignment2.bikes.Bike;

public interface BikeState {
    void handle(Bike bike);
}
