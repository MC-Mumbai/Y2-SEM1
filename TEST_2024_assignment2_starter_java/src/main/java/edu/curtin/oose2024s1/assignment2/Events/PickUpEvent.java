package edu.curtin.oose2024s1.assignment2.events;

import edu.curtin.oose2024s1.assignment2.states.AwaitingPickupState;
import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.bikes.Bike;
import edu.curtin.oose2024s1.assignment2.exceptions.BikeNotReadyException;
import edu.curtin.oose2024s1.assignment2.exceptions.NoMatchingEmailException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PickUpEvent implements Event {
    private static final Logger LOGGER = Logger.getLogger(PickUpEvent.class.getName());
    private String email;

    public PickUpEvent(String email) {
        this.email = email;
    }

    @Override
    public void execute(BicycleShop shop) {
        LOGGER.log(Level.INFO, () -> "Attempting to process pick-up for email: " + email);
        try {
            Bike bike = shop.getBikeForPickUp(email);
            if (bike != null) {
                if (bike.getState() instanceof AwaitingPickupState) {
                    shop.removeBike(bike);
                    shop.setBikesAwaitingPickup(shop.getBikesAwaitingPickup() - 1);
                    shop.notifyObservers("Pick accepted by: " + email);
                    LOGGER.log(Level.INFO, () -> "Pick-up accepted: Bike taken by customer with email: " + email);
                } else {
                    LOGGER.log(Level.WARNING, () -> "Attempted to pick up a bike not ready for pick-up.");
                    throw new BikeNotReadyException();
                }
            } else {
                LOGGER.log(Level.WARNING, () -> "No matching email found for pick-up: " + email);
                throw new NoMatchingEmailException();
            }
        } catch (NoMatchingEmailException | BikeNotReadyException e) {
            LOGGER.log(Level.SEVERE, () -> "Exception during pick-up: " + e.getMessage());
            shop.notifyObservers(e.getMessage());
        }
    }
}
