package edu.curtin.oose2024s1.assignment2;

public class PurchaseInStoreEvent implements Event {
    @Override
    public void execute(BicycleShop shop) {
        if (shop.getAvailableBikes() > 0) {
            shop.setAvailableBikes(shop.getAvailableBikes() - 1);
            shop.setCash(shop.getCash() + 1000);
            System.out.println("Purchase in-store accepted: Bike sold.");
        } else {
            System.out.println("FAILURE: No bikes left for in-store purchase.");
        }
    }
}
