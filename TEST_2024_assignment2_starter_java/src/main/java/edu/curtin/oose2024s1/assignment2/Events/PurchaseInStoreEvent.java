package edu.curtin.oose2024s1.assignment2.events;

import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.exceptions.NoBikesAvailableException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseInStoreEvent implements Event {
    private static final Logger LOGGER = Logger.getLogger(PurchaseInStoreEvent.class.getName());

    @Override
    public void execute(BicycleShop shop) {
        LOGGER.log(Level.INFO, () -> "Attempting to execute in-store purchase.");
        try {
            if (shop.getAvailableBikes() > 0) {
                shop.setAvailableBikes(shop.getAvailableBikes() - 1);
                shop.setCash(shop.getCash() + 1000);
                LOGGER.log(Level.INFO, () -> "Purchase in-store accepted: Bike sold.");
                shop.notifyObservers("Bike Sold Instore");
            } else {
                LOGGER.log(Level.WARNING, () -> "No bikes available for in-store purchase.");
                throw new NoBikesAvailableException("FAILURE: No bikes left for purchase.");
            }
        } catch (NoBikesAvailableException e) {
            LOGGER.log(Level.SEVERE, () -> "Exception during in-store purchase: " + e.getMessage());
            shop.notifyObservers(e.getMessage());
        }
    }
}
