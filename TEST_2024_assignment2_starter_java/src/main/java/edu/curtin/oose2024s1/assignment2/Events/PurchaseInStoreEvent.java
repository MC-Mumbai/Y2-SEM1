package edu.curtin.oose2024s1.assignment2.events;

import edu.curtin.oose2024s1.assignment2.bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.exceptions.NoBikesAvailableException;

public class PurchaseInStoreEvent implements Event {
    @Override
    public void execute(BicycleShop shop) {
        try {
            if (shop.getAvailableBikes() > 0) {
                shop.setAvailableBikes(shop.getAvailableBikes() - 1);
                shop.setCash(shop.getCash() + 1000);
                System.out.println("Purchase in-store accepted: Bike sold.");
                shop.notifyObservers("Bike Sold Instore");
                
            } else {
                throw new NoBikesAvailableException("FAILURE: No bikes left for purchase.");
            }
        } catch (NoBikesAvailableException e) {
            System.out.println(e.getMessage());
            shop.notifyObservers(e.getMessage());
        }
    }
}
