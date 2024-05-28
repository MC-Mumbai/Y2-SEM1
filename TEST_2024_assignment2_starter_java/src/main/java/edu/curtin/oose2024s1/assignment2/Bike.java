package edu.curtin.oose2024s1.assignment2;

public class Bike {
    private BikeState state;
    private String ownerEmail;
    private long serviceStartTime; // New field to track the start time of servicing

    public Bike(BikeState state) {
        this.state = state;
    }

    public Bike(BikeState state, String ownerEmail) {
        this.state = state;
        this.ownerEmail = ownerEmail;
    }

    public BikeState getState() {
        return state;
    }

    public void setState(BikeState state) {
        this.state = state;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public long getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(long serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public void performAction() {
        state.handle(this);
    }
}
