package edu.curtin.oose2024s1.assignment2.Bikes;

import java.util.ArrayList;
import java.util.List;

import edu.curtin.oose2024s1.assignment2.O.F.Factory;
import edu.curtin.oose2024s1.assignment2.O.F.Observer;
import edu.curtin.oose2024s1.assignment2.O.F.Subject;
import edu.curtin.oose2024s1.assignment2.States.AvailableState;
import edu.curtin.oose2024s1.assignment2.States.AwaitingPickupState;
import edu.curtin.oose2024s1.assignment2.States.BeingServicedState;

public class BicycleShop implements Subject {
    private int cash;
    private int availableBikes;
    private int bikesBeingServiced;
    private int bikesAwaitingPickup;
    private List<Bike> bicycles;
    private List<Observer> observers;

    public BicycleShop() {
        cash = 15000;
        availableBikes = 50;
        bikesBeingServiced = 0;
        bikesAwaitingPickup = 0;
        bicycles = new ArrayList<>();
        observers = new ArrayList<>();
        initialiseBikes();
    }

    private void initialiseBikes() {
        for (int i = 0; i < 50; i++) {
            bicycles.add(Factory.createBike(new AvailableState()));
        }
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
        notifyObservers("BikeShop Cash Updated: " + cash);
    }

    public int getAvailableBikes() {
        return availableBikes;
    }

    public void setAvailableBikes(int availableBikes) {
        this.availableBikes = availableBikes;
        notifyObservers("Available bikes updated: " + availableBikes);
    }

    public int getBikesBeingServiced() {
        return bikesBeingServiced;
    }

    public void setBikesBeingServiced(int bikesBeingServiced) {
        this.bikesBeingServiced = bikesBeingServiced;
        notifyObservers("Bikes being serviced updated: " + bikesBeingServiced);

    }

    public int getBikesAwaitingPickup() {
        return bikesAwaitingPickup;
    }

    public void setBikesAwaitingPickup(int bikesAwaitingPickup) {
        this.bikesAwaitingPickup = bikesAwaitingPickup;
        notifyObservers("Bikes Awaiting Pcikup updated: " + bikesAwaitingPickup);
    }

    public void addBike(Bike bike) {
        bicycles.add(bike);
        notifyObservers("Bike added: " + bike);
    }

    public void removeBike(Bike bike) {
    bicycles.remove(bike);
    notifyObservers("Bike removed: " + bike);
}

    public Bike getAvailableBike() {
        for (Bike bike : bicycles) {
            if (bike.getState() instanceof AvailableState) {
                return bike;
            }
        }
        return null;
    }

    public Bike getBikeForPickUp(String email) {
        for (Bike bike : bicycles) {
            if (bike.getOwnerEmail() != null && bike.getOwnerEmail().equals(email) && 
               (bike.getState() instanceof AwaitingPickupState || bike.getState() instanceof BeingServicedState)) {
                return bike;
            }
        }
        return null;
    }

    public List<Bike> getBikes() {
        return bicycles;
    }

    public void bikeServiceCompleted(Bike bike) {
        bikesBeingServiced--;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
