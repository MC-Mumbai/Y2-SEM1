package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    // Task class representing each task in the WBS
    static class Task {
        private String id;
        private String description;
        private int effortEstimate;
        private List<Task> subTasks;
    
        // Constructor
        public Task(String id, String description, int effortEstimate) {
            this.id = id;
            this.description = description;
            this.effortEstimate = effortEstimate;
            this.subTasks = new ArrayList<>();
        }
    
        // Getters
        public String getId() {
            return id;
        }
    
        public String getDescription() {
            return description;
        }
    
        public int getEffortEstimate() {
            return effortEstimate;
        }
    
        public List<Task> getSubTasks() {
            return subTasks;
        }
    
        // Setters
        public void setId(String id) {
            this.id = id;
        }
    
        public void setDescription(String description) {
            this.description = description;
        }
    
        public void setEffortEstimate(int effortEstimate) {
            this.effortEstimate = effortEstimate;
        }
    
        public void setSubTasks(List<Task> subTasks) {
            this.subTasks = subTasks;
        }
    
        // Method to add subtask
        public void addSubTask(Task task) {
            subTasks.add(task);
        }
    }
    

    // Class to manage WBS and perform operations
    static class WorkBreakdownSystem {
        private Task rootTask;

        // Constructor
        public WorkBreakdownSystem() {
            this.rootTask = new Task("", "Root", 0);
        }

        // Method to load WBS from file
        public void loadWBSFromFile(String filename) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Split the line into parts using semi-colon as delimiter
                    String[] parts = line.split(";");
    
                    // Clean up the parts (trim whitespace)
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = parts[i].trim();
                    }
    
                    // Extract information from the parts and create a Task object
                    String parentId = null;
                    String id = parts[1]; // Task id
                    String description = parts[2]; // Task description
                    int effortEstimate = 0; // Default effort estimate

                    if (parts.length > 3) {
                        try {
                            effortEstimate = Integer.parseInt(parts[3]); // Effort estimate
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid effort estimate: " + parts[3]);
                        }
                    }

                    if (parts.length > 4) {
                        parentId = parts[0]; // Parent id
                    }

                    Task task = new Task(id, description, effortEstimate);

    
                    // Add the created Task object to the appropriate location in the WBS hierarchy
                    // If parentId is null, assume it's a direct child of the root task
                    if (parentId == null) {
                        rootTask.addSubTask(task);
                    } else {
                        // Find the parent task and add this task as its subtask
                        Task parentTask = findTask(rootTask, parentId);
                        if (parentTask != null) {
                            parentTask.addSubTask(task);
                        } else {
                            System.out.println("Parent task not found for task " + id);
                        }
                    }
                }
                System.out.println("WBS loaded successfully.");
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error loading WBS from file: " + e.getMessage());
            }
        }

        // Helper method to find a task by its id recursively
        private Task findTask(Task task, String id) {
            if (task.getId().equals(id)) {
                return task;
            }
            for (Task subTask : task.getSubTasks()) {
                Task found = findTask(subTask, id);
                if (found != null) {
                    return found;
                }
            }
            return null;
        }

        // Method to save WBS to file
        public void saveWBSToFile(String filename) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                // Call recursive method to save tasks starting from the root task
                saveTaskToFile(writer, rootTask);
                System.out.println("WBS saved to file successfully.");
            } catch (IOException e) {
                System.out.println("Error saving WBS to file: " + e.getMessage());
            }
        }

        // Recursive method to save tasks and their subtasks
        private void saveTaskToFile(BufferedWriter writer, Task task) throws IOException {
            // Write task details to the file
            writer.write(task.toString());
            writer.newLine();

            // Save subtasks recursively
            for (Task subTask : task.getSubTasks()) {
                saveTaskToFile(writer, subTask);
            }
        }

        // Method to display WBS
        public void displayWBS() {
            // Call recursive method to display tasks starting from the root task
            displayTask(rootTask, 0);
        }

        // Recursive method to display tasks and their subtasks with proper indentation
        private void displayTask(Task task, int depth) {
            // Indent based on the depth
            for (int i = 0; i < depth; i++) {
                System.out.print("    ");
            }

            // Display task details
            System.out.println(task.getId() + " ; " + task.getDescription() + " ; " + task.getEffortEstimate());

            // Display subtasks recursively
            for (Task subTask : task.getSubTasks()) {
                displayTask(subTask, depth + 1);
            }
        }

        // Method to estimate effort
        public void estimateEffort() {
            int totalEffort = calculateTotalEffort(rootTask);
            System.out.println("Total Effort Estimate: " + totalEffort);
        }

        // Recursive method to calculate total effort
        private int calculateTotalEffort(Task task) {
            int totalEffort = task.getEffortEstimate();

            // Calculate effort for subtasks recursively
            for (Task subTask : task.getSubTasks()) {
                totalEffort += calculateTotalEffort(subTask);
            }

            return totalEffort;
        }

        // Method to configure settings
        public void configureSettings() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the ID of the task to configure: ");
            String taskId = scanner.nextLine();

            Task task = findTaskById(rootTask, taskId);
            if (task != null) {
                System.out.print("Enter the new effort estimate for task " + taskId + ": ");
                int newEffortEstimate = scanner.nextInt();
                task.setEffortEstimate(newEffortEstimate);
                System.out.println("Effort estimate for task " + taskId + " updated successfully.");
            } else {
                System.out.println("Task with ID " + taskId + " not found.");
            }
        }

        // Helper method to find a task by its ID
        private Task findTaskById(Task task, String taskId) {
            if (task.getId().equals(taskId)) {
                return task;
            }
            for (Task subTask : task.getSubTasks()) {
                Task foundTask = findTaskById(subTask, taskId);
                if (foundTask != null) {
                    return foundTask;
                }
            }
            return null;
        }
    }

    
        public static void main(String[] args) {
            WorkBreakdownSystem wbs = new WorkBreakdownSystem();

            // Load data from file provided as command-line argument
            if (args.length > 0) {
                wbs.loadWBSFromFile(args[0]);
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
                        wbs.displayWBS();
                        wbs.estimateEffort();
                        break;
                    case 2:
                        wbs.configureSettings();
                        break;
                    case 3:
                        // Save data to file before quitting
                        wbs.saveWBSToFile(args[0]);
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
            scanner.close();
        }
}
