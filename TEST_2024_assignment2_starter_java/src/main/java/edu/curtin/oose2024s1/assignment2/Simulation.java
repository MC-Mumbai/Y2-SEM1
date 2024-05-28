package edu.curtin.oose2024s1.assignment2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Simulation {
    private BicycleShop shop;
    private BikeShopInput input;
    private PrintWriter writer;
    private EmployeeSalary employeeSalary;

    public Simulation(BicycleShop shop, BikeShopInput input) {
        this.shop = shop;
        this.input = input;
        this.employeeSalary = new EmployeeSalary(shop); // Initialize EmployeeSalary

        try {
            writer = new PrintWriter(new FileWriter("sim_results.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BicycleShop getShop() {
        return shop;
    }

    public void run() {
        int daysElapsed = 0;
        int totalMessages = 0;
        int totalFailures = 0;

        try {
            while (System.in.available() == 0) {
                Thread.sleep(1000); // Simulate 1 day as 1 second
                daysElapsed++;
                shop.notifyObservers("Day " + daysElapsed);

                // Check and process employee salary payment
                employeeSalary.checkAndPaySalary();

                // Process new messages
                String message;
                while ((message = input.nextMessage()) != null) {
                    totalMessages++;
                    if (!processMessage(message)) {
                        totalFailures++;
                    }
                    logMessage(message);
                }

                // Update the state of all bikes
                List<Bike> bikes = shop.getBikes();
                for (Bike bike : bikes) {
                    bike.performAction();
                }

                // Output daily statistics
                displayDailyStatistics(daysElapsed);
            }

            // Final statistics
            displayFinalStatistics(totalMessages, totalFailures);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    private boolean processMessage(String message) {
        String[] parts = message.split(" ", 2);
        String command = parts[0];
        String email = parts.length > 1 ? parts[1] : null;

        try {
            Event event = Factory.createEvent(command, email);
            event.execute(shop);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid message: " + message);
            return false;
        }
    }

    private void logMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    public void displayDailyStatistics(int daysElapsed) {
        System.out.println("Day: " + daysElapsed);
        System.out.println("Cash: " + shop.getCash());
        System.out.println("Available Bikes: " + shop.getAvailableBikes());
        System.out.println("Bikes Being Serviced: " + shop.getBikesBeingServiced());
        System.out.println("Bikes Awaiting Pickup: " + shop.getBikesAwaitingPickup());
    }

    public void displayFinalStatistics(int totalMessages, int totalFailures) {
        System.out.println("Total messages: " + totalMessages);
        System.out.println("Total failures: " + totalFailures);
    }
}
