package edu.curtin.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class WBSLoader {
    public static void loadWBSFromFile(String filename, Map<String, Task> taskMap) {
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
                String parentId = parts.length > 4 ? parts[0] : parts[0].isEmpty() ? null : parts[0];
                String id = parts[1]; // Task id
                String description = parts[2]; // Task description
                int effortEstimate = parts.length > 3 ? Integer.parseInt(parts[3]) : 0; // Effort estimate

                Task task = new Task(id, description, effortEstimate, parentId);
                task.setParentTaskID(parentId); // Set the parent task ID

                // Add the created Task object to the appropriate location in the WBS hierarchy
                if (parentId != null) {
                    // Find the parent task and add this task as its subtask
                    Task parentTask = taskMap.get(parentId);
                    if (parentTask != null) {
                        parentTask.addSubTask(task);
                    } else {
                        System.out.println("Parent task not found for task " + id);
                    }
                }

                // Add the task to the map
                taskMap.put(id, task);
            }
            System.out.println("WBS loaded successfully.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading WBS from file: " + e.getMessage());
        }
    }
}
