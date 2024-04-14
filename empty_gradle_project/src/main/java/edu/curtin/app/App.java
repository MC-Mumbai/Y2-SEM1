package edu.curtin.app;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
public class App {
    
    public static void main(String[] args) {
        

        Map<String, Task> taskMap = new HashMap<>(); // Create a map to hold tasks

        // Load data from file and populate the task map
        if (args.length > 0) {
            WBSLoader.loadWBSFromFile(args[0], taskMap);
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
                    //WBSDisplayer.displayWBS(rootTask);
                    
                    break;
                case 2:
                    // Configure settings
                    //WBSConfigurator.configureSettings(rootTask);
                    break;
                case 3:
                    // Save data to file before quitting
                    //WBSSaver.saveWBSToFile(args[0], rootTask);
                    quit = true;
                    break;
                case 4:
                    // Display task and subtasks
                    WBSDisplayer.displayTaskAndSubtasks(taskMap);
                    WBSConfigurator.displayEffortSummary(taskMap);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
