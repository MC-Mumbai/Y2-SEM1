package edu.curtin.oose2024s1.assignment2.events;

import edu.curtin.oose2024s1.assignment2.states.AvailableState;
import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.exceptions.NotEnoughResourcesForDeliveryException;
import edu.curtin.oose2024s1.assignment2.o_f.Factory;

public class DeliveryEvent implements Event {
    @Override
    public void execute(BicycleShop shop) {
        try {
            if (shop.getAvailableBikes() <= 90 && shop.getCash() >= 5000) {
                shop.setAvailableBikes(shop.getAvailableBikes() + 10);
                shop.setCash(shop.getCash() - 5000);
                for (int i = 0; i < 10; i++) {
                    shop.addBike(Factory.createBike(new AvailableState()));
                }
                shop.notifyObservers("Delivery accepted: 10 bikes added.");
            } else {
                throw new NotEnoughResourcesForDeliveryException();
            }
        } catch (NotEnoughResourcesForDeliveryException e) {
            // Handle exception, e.g., log it and notify observers of the failure
            shop.notifyObservers("Failed to process delivery: " + e.getMessage());
        }
    }
}
