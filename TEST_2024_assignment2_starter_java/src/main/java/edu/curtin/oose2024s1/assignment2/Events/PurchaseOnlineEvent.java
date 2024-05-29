package edu.curtin.oose2024s1.assignment2.events;

import edu.curtin.oose2024s1.assignment2.states.AwaitingPickupState;
import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.bikes.Bike;
import edu.curtin.oose2024s1.assignment2.exceptions.NoBikesAvailableException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PurchaseOnlineEvent implements Event {
    private static final Logger LOGGER = Logger.getLogger(PurchaseOnlineEvent.class.getName());
    private String email;

    public PurchaseOnlineEvent(String email) {
        this.email = email;
    }

    @Override
    public void execute(BicycleShop shop) {
        LOGGER.log(Level.INFO, () -> "Attempting to execute online purchase for email: " + email);
        try {
            if (shop.getAvailableBikes() > 0) {
                Bike bike = shop.getAvailableBike();
                if (bike != null) {
                    bike.setOwnerEmail(email);
                    bike.setState(new AwaitingPickupState());
                    shop.setAvailableBikes(shop.getAvailableBikes() - 1);
                    shop.setBikesAwaitingPickup(shop.getBikesAwaitingPickup() + 1);
                    shop.setCash(shop.getCash() + 1000);
                    LOGGER.log(Level.INFO, () -> "Purchase online accepted: Bike reserved for " + email);
                    shop.notifyObservers("Bike Sold Online");
                } else {
                    LOGGER.log(Level.WARNING, () -> "No available bike found for online purchase.");
                    throw new NoBikesAvailableException("FAILURE: No bikes left for online purchase.");
                }
            } else {
                LOGGER.log(Level.WARNING, () -> "Not enough bikes available for online purchase.");
                throw new NoBikesAvailableException("FAILURE: No bikes left for online purchase.");
            }
        } catch (NoBikesAvailableException e) {
            LOGGER.log(Level.SEVERE, () -> "Exception during online purchase: " + e.getMessage());
            shop.notifyObservers(e.getMessage());
        }
    }
}
