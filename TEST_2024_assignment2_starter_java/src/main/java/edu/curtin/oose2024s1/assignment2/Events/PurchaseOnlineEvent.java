package edu.curtin.oose2024s1.assignment2.Events;

import edu.curtin.oose2024s1.assignment2.Bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.Bikes.Bike;
import edu.curtin.oose2024s1.assignment2.States.AwaitingPickupState;

public class PurchaseOnlineEvent implements Event {
    private String email;

    public PurchaseOnlineEvent(String email) {
        this.email = email;
    }

    @Override
    public void execute(BicycleShop shop) {
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
            System.out.println("FAILURE: No bikes left for online purchase.");
        }
    }
}
