package edu.curtin.app;

import java.util.Scanner;
public class App {

    // Class to manage WBS and perform operations
    static class WorkBreakdownSystem {
        private Task rootTask;

        // Constructor
        public WorkBreakdownSystem() {
            this.rootTask = new Task("", "Root", 0);
        }
    }
    
    public static void main(String[] args) {
        Task rootTask = new Task("", "Root", 0);

        // Load data from file
        if (args.length > 0) {
            WBSLoader.loadWBSFromFile(args[0], rootTask);
        } else {
            System.out.println("No filename provided.");
            return;
        }

        // Display menu and handle user input
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            System.out.println("Menu:");
            System.out.println("1. Estimate effort");
            System.out.println("2. Configure");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Display WBS and effort summary
                    WBSDisplayer.displayWBS(rootTask);
                    WBSDisplayer.estimateEffort(rootTask);
                    break;
                case 2:
                    // Configure settings
                    WBSConfigurator.configureSettings(rootTask);
                    break;
                case 3:
                    // Save data to file before quitting
                    WBSSaver.saveWBSToFile(args[0], rootTask);
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
