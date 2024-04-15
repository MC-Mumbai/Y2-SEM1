package edu.curtin.app;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class WBSSaver {
    public static void saveWBSToFile(Map<String, Task> taskMap, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Task task : taskMap.values()) {
                saveTask(writer, task, "");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file: " + e.getMessage());
        }
    }

    private static void saveTask(BufferedWriter writer, Task task, String prefix) throws IOException {
        writer.write(task.getParentTaskID() + " ; " + task.getId() + " ; " + task.getDescription() + " ; " + task.getEffortEstimate());
        writer.newLine();

        
    }
}

