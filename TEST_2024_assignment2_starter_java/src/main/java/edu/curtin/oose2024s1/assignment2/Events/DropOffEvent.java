package edu.curtin.oose2024s1.assignment2.events;

import edu.curtin.oose2024s1.assignment2.states.BeingServicedState;
import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.bikes.Bike;
import edu.curtin.oose2024s1.assignment2.exceptions.NotEnoughSpaceForDropOffException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DropOffEvent implements Event {
    private static final Logger LOGGER = Logger.getLogger(DropOffEvent.class.getName());
    private String email;

    public DropOffEvent(String email) {
        this.email = email;
    }

    @Override
    public void execute(BicycleShop shop) {
        LOGGER.log(Level.INFO, () -> "Attempting to process drop-off for email: " + email);
        try {
            if (shop.getAvailableBikes() + shop.getBikesBeingServiced() + shop.getBikesAwaitingPickup() < 100) {
                Bike bike = new Bike(new BeingServicedState());
                bike.setOwnerEmail(email);
                bike.setServiceStartTime(System.currentTimeMillis());
                shop.addBike(bike);
                shop.setBikesBeingServiced(shop.getBikesBeingServiced() + 1);
                shop.setCash(shop.getCash() + 100);
                shop.notifyObservers("Bike in Service");
                LOGGER.log(Level.INFO, () -> "Drop-off accepted: Bike is being serviced for " + email);
            } else {
                LOGGER.log(Level.WARNING, () -> "Not enough space for drop-off.");
                throw new NotEnoughSpaceForDropOffException();
            }
        } catch (NotEnoughSpaceForDropOffException e) {
            LOGGER.log(Level.SEVERE, () -> "Exception during drop-off: " + e.getMessage());
            shop.notifyObservers(e.getMessage());
        }
    }
}
