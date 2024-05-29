package edu.curtin.oose2024s1.assignment2.events;

import edu.curtin.oose2024s1.assignment2.states.AvailableState;
import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.exceptions.NotEnoughResourcesForDeliveryException;
import edu.curtin.oose2024s1.assignment2.o_f.Factory;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeliveryEvent implements Event {
    private static final Logger LOGGER = Logger.getLogger(DeliveryEvent.class.getName());

    @Override
    public void execute(BicycleShop shop) {
        LOGGER.log(Level.INFO, () -> "Attempting to execute delivery event.");
        try {
            if (shop.getAvailableBikes() <= 90 && shop.getCash() >= 5000) {
                LOGGER.log(Level.INFO, () -> "Sufficient resources confirmed. Proceeding with delivery.");
                shop.setAvailableBikes(shop.getAvailableBikes() + 10);
                shop.setCash(shop.getCash() - 5000);
                for (int i = 0; i < 10; i++) {
                    shop.addBike(Factory.createBike(new AvailableState()));
                }
                LOGGER.log(Level.INFO, () -> "Delivery successful. 10 bikes added.");
                shop.notifyObservers("Delivery accepted: 10 bikes added.");
            } else {
                LOGGER.log(Level.WARNING, () -> "Not enough resources for delivery. Throwing exception.");
                throw new NotEnoughResourcesForDeliveryException();
            }
        } catch (NotEnoughResourcesForDeliveryException e) {
            LOGGER.log(Level.SEVERE, () -> "Failed to process delivery: " + e.getMessage());
            shop.notifyObservers("Failed to process delivery: " + e.getMessage());
        }
    }
}
