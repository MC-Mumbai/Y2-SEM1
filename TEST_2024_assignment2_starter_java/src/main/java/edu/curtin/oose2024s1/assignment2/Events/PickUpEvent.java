package edu.curtin.oose2024s1.assignment2.Events;

import edu.curtin.oose2024s1.assignment2.Bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.Bikes.Bike;
import edu.curtin.oose2024s1.assignment2.States.AwaitingPickupState;
import edu.curtin.oose2024s1.assignment2.States.BeingServicedState;

public class PickUpEvent implements Event {
    private String email;

    public PickUpEvent(String email) {
        this.email = email;
    }

    @Override
    public void execute(BicycleShop shop) {
        Bike bike = shop.getBikeForPickUp(email);
        if (bike != null) {
            if (bike.getState() instanceof AwaitingPickupState) {
                shop.removeBike(bike);
                shop.setBikesAwaitingPickup(shop.getBikesAwaitingPickup() - 1);
                System.out.println("Pick-up accepted: Bike taken by customer.");
            } else if (bike.getState() instanceof BeingServicedState) {
                System.out.println("FAILURE: Bike not ready (still being serviced).");
            }
        } else {
            System.out.println("FAILURE: No bike matching customer email.");
        }
    }
}
