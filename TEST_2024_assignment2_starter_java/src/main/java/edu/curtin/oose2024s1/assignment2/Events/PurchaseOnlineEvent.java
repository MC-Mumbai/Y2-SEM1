package edu.curtin.oose2024s1.assignment2.events;

import edu.curtin.oose2024s1.assignment2.states.AwaitingPickupState;
import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.bikes.Bike;
import edu.curtin.oose2024s1.assignment2.exceptions.NoBikesAvailableException;

public class PurchaseOnlineEvent implements Event {
    private String email;

    public PurchaseOnlineEvent(String email) {
        this.email = email;
    }

    @Override
    public void execute(BicycleShop shop) {
        try {
            if (shop.getAvailableBikes() > 0) {
                Bike bike = shop.getAvailableBike();
                if (bike != null) {
                    bike.setOwnerEmail(email);
                    bike.setState(new AwaitingPickupState());
                    shop.setAvailableBikes(shop.getAvailableBikes() - 1);
                    shop.setBikesAwaitingPickup(shop.getBikesAwaitingPickup() + 1);
                    shop.setCash(shop.getCash() + 1000);
                    System.out.println("Purchase online accepted: Bike is awaiting pickup.");
                }
            } else {
                throw new NoBikesAvailableException("FAILURE: No bikes left for online purchase.");
            }
        } catch (NoBikesAvailableException e) {
            System.out.println(e.getMessage());
            shop.notifyObservers(e.getMessage());
        }
    }
}
