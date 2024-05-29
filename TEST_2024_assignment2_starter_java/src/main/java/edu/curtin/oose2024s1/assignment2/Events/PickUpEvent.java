package edu.curtin.oose2024s1.assignment2.events;

import edu.curtin.oose2024s1.assignment2.states.AwaitingPickupState;
import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.bikes.Bike;
import edu.curtin.oose2024s1.assignment2.exceptions.BikeNotReadyException;
import edu.curtin.oose2024s1.assignment2.exceptions.NoMatchingEmailException;

public class PickUpEvent implements Event {
    private String email;

    public PickUpEvent(String email) {
        this.email = email;
    }

    @Override
    public void execute(BicycleShop shop) {
        try {
            Bike bike = shop.getBikeForPickUp(email);
            if (bike != null) {
                if (bike.getState() instanceof AwaitingPickupState) {
                    shop.removeBike(bike);
                    shop.setBikesAwaitingPickup(shop.getBikesAwaitingPickup() - 1);
                    System.out.println("Pick-up accepted: Bike taken by customer.");
                } else {
                    throw new BikeNotReadyException();
                }
            } else {
                throw new NoMatchingEmailException();
            }
        } catch (NoMatchingEmailException | BikeNotReadyException e) {
            System.out.println(e.getMessage());
            shop.notifyObservers(e.getMessage());
        }
    }
}
