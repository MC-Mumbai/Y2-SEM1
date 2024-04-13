package edu.curtin.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WBSLoader {
    public static void loadWBSFromFile(String filename, Task rootTask) {
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
    private static Task findTask(Task task, String id) {
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
}
