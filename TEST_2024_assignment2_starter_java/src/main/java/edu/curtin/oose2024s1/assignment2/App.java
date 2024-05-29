package edu.curtin.oose2024s1.assignment2;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.curtin.oose2024s1.assignment2.Bikes.BicycleShop;
import edu.curtin.oose2024s1.assignment2.Bikes.Bike;
import edu.curtin.oose2024s1.assignment2.Events.Event;
import edu.curtin.oose2024s1.assignment2.O.F.Factory;

public class App {
    public BicycleShop shop;
    public BikeShopInput input;
    public EmployeeSalary employeeSalary;
    public static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public App() {
        this.shop = new BicycleShop();
        this.input = new BikeShopInput();  // Optionally, use a seed for reproducibility
        this.employeeSalary = new EmployeeSalary(shop); // Initialize EmployeeSalary
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
                employeeSalary.checkAndPaySalary();
                String message;
                while ((message = input.nextMessage()) != null) {
                    totalMessages++;
                    if (!processMessage(message)) {
                        totalFailures++;
                    }
                    logMessage(message);
                }
                List<Bike> bikes = shop.getBikes();
                for (Bike bike : bikes) {
                    bike.performAction();
                }
                displayDailyStatistics(daysElapsed);
            }
            displayFinalStatistics(totalMessages, totalFailures);
        } catch (InterruptedException | IOException e) {
            LOGGER.log(Level.SEVERE, "Exception occurred during the simulation", e);
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
        // Use try-with-resources to ensure the PrintWriter is closed after use
        try (PrintWriter writer = new PrintWriter(new FileWriter("sim_results.txt", true))) {
            writer.println(message);
            writer.flush(); // Ensure the message is actually written to the file
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to write message to file", e);
        }
    }

    public void displayDailyStatistics(int daysElapsed) {
        String stats = "Day: " + daysElapsed + "\n" +
                       "Cash: " + shop.getCash() + "\n" +
                       "Available Bikes: " + shop.getAvailableBikes() + "\n" +
                       "Bikes Being Serviced: " + shop.getBikesBeingServiced() + "\n" +
                       "Bikes Awaiting Pickup: " + shop.getBikesAwaitingPickup();
        System.out.println(stats);
        logMessage(stats); // Log the daily statistics
    }

    public void displayFinalStatistics(int totalMessages, int totalFailures) {
        String finalStats = "Total messages: " + totalMessages + "\n" +
                            "Total failures: " + totalFailures;
        System.out.println(finalStats);
        logMessage(finalStats); // Log the final statistics
    }
    

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }
}
