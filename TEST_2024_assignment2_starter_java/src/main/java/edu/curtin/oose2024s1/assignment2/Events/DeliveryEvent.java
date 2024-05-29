package edu.curtin.oose2024s1.assignment2.Events;

import edu.curtin.oose2024s1.assignment2.Bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.O.F.Factory;
import edu.curtin.oose2024s1.assignment2.States.AvailableState;

public class DeliveryEvent implements Event {
    @Override
    public void execute(BicycleShop shop) {
        if (shop.getAvailableBikes() <= 90 && shop.getCash() >= 5000) {
            shop.setAvailableBikes(shop.getAvailableBikes() + 10);
            shop.setCash(shop.getCash() - 5000);
            for (int i = 0; i < 10; i++) {
                shop.addBike(Factory.createBike(new AvailableState()));
            }
            shop.notifyObservers("Delivery accepted: 10 bikes added.");
        } else {
            shop.notifyObservers("FAILURE: Not enough space or cash for delivery.");
        }
    }
}

