package edu.curtin.app;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Arrays;

public class WBSConfigurator {
    private static Scanner scanner = new Scanner(System.in);
    private static int defaultNumberOfEstimates = 3; // Default value
    private static int defaultReconciliationApproach = 3; // Default reconciliation approach

    public static void estimateEffort(Map<String, Task> taskMap) {
        // Ask for a task ID
        System.out.print("Enter the task ID to estimate effort: ");
        String taskId = scanner.nextLine();

        // Find the task by ID
        Task task = taskMap.get(taskId);
        if (task != null) {
            // If the task has subtasks, estimate effort for each subtask
            if (!task.getSubTasks().isEmpty()) {
                for (Task subTask : task.getSubTasks()) {
                    estimateSubtaskEffort(subTask);
                }
            } else {
                // If the task has no subtasks, estimate effort for the task itself
                estimateSubtaskEffort(task);
            }
        } else {
            System.out.println("Task with ID " + taskId + " not found.");
        }
    }

    private static void estimateSubtaskEffort(Task task) {
        // Ask for separate effort estimates
        System.out.print("Enter separate effort estimates for task " + task.getId() + ": ");
        String[] estimates = scanner.nextLine().split(" ");

        // Ensure the number of estimates matches the default number
        while (estimates.length != defaultNumberOfEstimates) {
            System.out.println("Invalid number of estimates. Please enter " + defaultNumberOfEstimates + " estimates:");
            String input = scanner.nextLine();
            estimates = input.split(" ");
        }

        // Check if all estimates are the same
        boolean allSame = areAllSame(estimates);

        if (!allSame) {
            // Prompt the user to choose a new reconciliation approach
            System.out.println("Two or more estimates are different. Choose a new reconciliation approach:");
            System.out.println("1. Take the highest estimate");
            System.out.println("2. Take the median estimate");
            System.out.println("3. Use Default Approach");

            // Get the user's choice
            int choice = Integer.parseInt(scanner.nextLine());

            // Perform reconciliation based on the user's choice
            switch (choice) {
                case 1:
                    reconcileTakeHighest(estimates);
                    break;
                case 2:
                    reconcileTakeMedian(estimates);
                    break;
                case 3:
                    reconcileEffortEstimates(estimates);
                    break;
                default:
                    System.out.println("Invalid choice. Using default reconciliation approach.");
                    reconcileEffortEstimates(estimates); // Use default reconciliation approach
                    break;
            }
        } else {
            System.out.println("All estimates are the same: " + estimates[0]);
        }

        // Display separate effort estimates
        System.out.println("Separate Effort Estimates:");
        for (int i = 0; i < estimates.length; i++) {
            System.out.println("Estimator " + (i + 1) + ": " + estimates[i]);
        }

        // Record the new effort estimate within the WBS
        // (Implementation of reconciliation and recording to be done)
    }

    
    private static void reconcileEffortEstimates(String[] estimates) {
        // Implement default reconciliation approach here
        // Use defaultReconciliationApproach to determine the approach
        
        switch (defaultReconciliationApproach) {
            case 1:
                reconcileTakeHighest(estimates);
                break;
            case 2:
                reconcileTakeMedian(estimates);
                break;
            case 3:
                reconcileRequestRevised(estimates);
                break;
            default:
                System.out.println("Invalid default reconciliation approach.");
                // You can implement a default behavior here if needed
        }
    }
    

    // Helper method to reconcile differing estimates by taking the highest estimate
    private static void reconcileTakeHighest(String[] estimates) {
        int highest = Integer.MIN_VALUE;
        for (String estimate : estimates) {
            int value = Integer.parseInt(estimate);
            if (value > highest) {
                highest = value;
            }
        }
        System.out.println("Reconciled estimate: " + highest);
    }
    
    // Helper method to reconcile differing estimates by taking the median estimate
    private static void reconcileTakeMedian(String[] estimates) {
        int[] sortedEstimates = Arrays.stream(estimates).mapToInt(Integer::parseInt).sorted().toArray();
        int median;
        if (sortedEstimates.length % 2 == 0) {
            median = (sortedEstimates[sortedEstimates.length / 2 - 1] + sortedEstimates[sortedEstimates.length / 2]) / 2;
        } else {
            median = sortedEstimates[sortedEstimates.length / 2];
        }
        System.out.println("Reconciled estimate: " + median);
    }

    // Reconciliation approach: Request a single revised estimate
    private static void reconcileRequestRevised(String[] estimates) {
        // Print the original estimates for reference
        System.out.println("Original Estimates:");
        for (int i = 0; i < estimates.length; i++) {
            System.out.println((i + 1) + ": " + estimates[i]);
        }
        
        // Prompt the user to select one estimate
        int chosenIndex = -1;
        while (chosenIndex < 0 || chosenIndex >= estimates.length) {
            System.out.print("Enter the index of the revised estimate: ");
            chosenIndex = scanner.nextInt() - 1; // Adjust for 0-based indexing
            scanner.nextLine(); // Consume newline
            if (chosenIndex < 0 || chosenIndex >= estimates.length) {
                System.out.println("Invalid index. Please enter a valid index.");
            }
        }
        
        // Set the chosen estimate as the new estimate
        String revisedEstimate = estimates[chosenIndex];
        System.out.println("Revised Estimate: " + revisedEstimate);
    }
    
    private static boolean areAllSame(String[] estimates) {
        // Check if all elements in the array are the same
        for (int i = 1; i < estimates.length; i++) {
            if (!estimates[i].equals(estimates[0])) {
                return false;
            }
        }
        return true;
    }

    public static void configureOptions() {
        // Ask for a new value of numberOfEstimates
        System.out.print("Enter a new value for the number of estimates (N): ");
        int newNumberOfEstimates = Integer.parseInt(scanner.nextLine());

        // Validate the new value of numberOfEstimates
        if (newNumberOfEstimates <= 0) {
            System.out.println("Invalid value for the number of estimates. Please enter a positive integer.");
            return;
        }

        // Ask the user to choose a new reconciliation approach
        System.out.println("Choose a new reconciliation approach:");
        System.out.println("1. Take the highest estimate");
        System.out.println("2. Take the median estimate");
        System.out.println("3. Request a single revised estimate");

        // Get the user's choice
        int choice = Integer.parseInt(scanner.nextLine());

        // Set the new configuration
        switch (choice) {
            case 1:
                WBSConfigurator.setReconciliationApproach(1);
                break;
            case 2:
                WBSConfigurator.setReconciliationApproach(2);
                break;
            case 3:
                WBSConfigurator.setReconciliationApproach(3);
                break;
            default:
                System.out.println("Invalid choice. Using default reconciliation approach.");
        }

        // Update the default number of estimates
        WBSConfigurator.setDefaultNumberOfEstimates(newNumberOfEstimates);
    }

    private static void setReconciliationApproach(int approach) {
        // Set the new reconciliation approach
        System.out.println("Reconciliation approach set to: " + approach);
        // Update the reconciliation approach (implementation to be done)
        defaultReconciliationApproach = approach; // Update default reconciliation approach
    }

    private static void setDefaultNumberOfEstimates(int newNumberOfEstimates) {
        // Set the new default number of estimates
        defaultNumberOfEstimates = newNumberOfEstimates;
        System.out.println("Default number of estimates set to: " + defaultNumberOfEstimates);
    }
    
}
