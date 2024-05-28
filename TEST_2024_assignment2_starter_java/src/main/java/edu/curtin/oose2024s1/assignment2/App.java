package edu.curtin.oose2024s1.assignment2;

public class App {
    public static void main(String[] args) {

        /* 
        BicycleShop shop = new BicycleShop();
        BikeShopInput input = new BikeShopInput();  // Optionally, use a seed for reproducibility
        Simulation simulation = new Simulation(shop, input);
        simulation.run();
        */

        BicycleShop shop = new BicycleShop();
        BikeShopInput input = new BikeShopInput();  // Optionally, use a seed for reproducibility
        Simulation simulation = new Simulation(shop, input);
        
        

        // Check status
        simulation.run();



    }
}
