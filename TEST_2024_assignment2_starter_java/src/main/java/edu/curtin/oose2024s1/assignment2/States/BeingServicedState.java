package edu.curtin.oose2024s1.assignment2.states;

import edu.curtin.oose2024s1.assignment2.bikes.Bike;

public class BeingServicedState implements BikeState {
    private static final long SERVICE_DURATION = 2000; // 2 seconds

    @Override
    public void handle(Bike bike) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - bike.getServiceStartTime();

        if (elapsedTime >= SERVICE_DURATION) {
            System.out.println("Bike service completed. Changing state to AwaitingPickup.");
            
            bike.setState(new AwaitingPickupState());
        } else {
            System.out.println("Bike is still being serviced.");
        }
    }
}
