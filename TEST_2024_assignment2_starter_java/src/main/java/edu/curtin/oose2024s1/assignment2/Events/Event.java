package edu.curtin.oose2024s1.assignment2.events;

import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;

public interface Event {
    void execute(BicycleShop shop);
}
