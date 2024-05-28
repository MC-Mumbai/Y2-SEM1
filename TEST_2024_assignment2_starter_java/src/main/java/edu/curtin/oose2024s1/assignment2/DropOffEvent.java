package edu.curtin.oose2024s1.assignment2;

public class DropOffEvent implements Event {
    private String email;

    public DropOffEvent(String email) {
        this.email = email;
    }

    @Override
    public void execute(BicycleShop shop) {
        if (shop.getAvailableBikes() + shop.getBikesBeingServiced() + shop.getBikesAwaitingPickup() < 100) {
            Bike bike = new Bike(new BeingServicedState());
            bike.setOwnerEmail(email);
            bike.setServiceStartTime(System.currentTimeMillis()); // Set the service start time
            shop.addBike(bike);
            shop.setBikesBeingServiced(shop.getBikesBeingServiced() + 1);
            shop.setCash(shop.getCash() + 100);
            System.out.println("Drop-off accepted: Bike is being serviced.");
        } else {
            System.out.println("FAILURE: Not enough space for drop-off.");
        }
    }
}
