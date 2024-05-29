package edu.curtin.oose2024s1.assignment2.events;

import edu.curtin.oose2024s1.assignment2.states.BeingServicedState;
import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.bikes.Bike;
import edu.curtin.oose2024s1.assignment2.exceptions.NotEnoughSpaceForDropOffException;

public class DropOffEvent implements Event {
    private String email;

    public DropOffEvent(String email) {
        this.email = email;
    }

    @Override
    public void execute(BicycleShop shop) {
        try {
            if (shop.getAvailableBikes() + shop.getBikesBeingServiced() + shop.getBikesAwaitingPickup() < 100) {
                Bike bike = new Bike(new BeingServicedState());
                bike.setOwnerEmail(email);
                bike.setServiceStartTime(System.currentTimeMillis());
                shop.addBike(bike);
                shop.setBikesBeingServiced(shop.getBikesBeingServiced() + 1);
                shop.setCash(shop.getCash() + 100);
                System.out.println("Drop-off accepted: Bike is being serviced.");
            } else {
                throw new NotEnoughSpaceForDropOffException();
            }
        } catch (NotEnoughSpaceForDropOffException e) {
            System.out.println(e.getMessage());
            shop.notifyObservers(e.getMessage());
        }
    }
}
