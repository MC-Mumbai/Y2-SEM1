package edu.curtin.oose2024s1.assignment2;

public class DeliveryEvent implements Event {
    @Override
    public void execute(BicycleShop shop) {
        if (shop.getAvailableBikes() + 10 <= 100 && shop.getCash() >= 5000) {
            shop.setAvailableBikes(shop.getAvailableBikes() + 10);
            shop.setCash(shop.getCash() - 5000);
            System.out.println("Delivery accepted: 10 bikes added.");
        } else {
            System.out.println("FAILURE: Not enough space or cash for delivery.");
        }
    }
}
