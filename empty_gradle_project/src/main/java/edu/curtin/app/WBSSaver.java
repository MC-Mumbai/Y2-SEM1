package edu.curtin.app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class WBSSaver {
    // Method to save WBS to file
    public static void saveWBSToFile(String filename, Task rootTask) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Call recursive method to save tasks starting from the root task
            saveTaskToFile(writer, rootTask, "");
            System.out.println("WBS saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving WBS to file: " + e.getMessage());
        }
    }

    // Recursive method to save tasks and their subtasks
    private static void saveTaskToFile(BufferedWriter writer, Task task, String parentID) throws IOException {
        // Construct the path for the current task
        String taskPath = parentID.isEmpty() ? ";" : parentID;

        // Write the task path and other details to the file
        writer.write(taskPath + " ; " + task.getId() + " ; " + task.getDescription() + " ; " + task.getEffortEstimate());
        writer.newLine();

        // Save subtasks recursively with updated parent path
        for (Task subTask : task.getSubTasks()) {
            saveTaskToFile(writer, subTask, taskPath);
        }
    }
}
