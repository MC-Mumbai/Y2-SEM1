package edu.curtin.app;

import java.util.List;
import java.util.Map;

public class WBSConfigurator {
    // Method to display effort summary
    public static void displayEffortSummary(Map<String, Task> taskMap) {
        // Calculate and display total known effort
        int totalKnownEffort = calculateTotalKnownEffort(taskMap);
        int unknownTasksCount = countUnknownTasks(taskMap);
        System.out.println("Total known effort = " + totalKnownEffort);
        System.out.println("Unknown tasks = " + unknownTasksCount);
    }

    // Method to calculate total known effort
    private static int calculateTotalKnownEffort(Map<String, Task> taskMap) {
        int totalEffort = 0;

        // Calculate effort for all tasks in the map
        for (Task task : taskMap.values()) {
            totalEffort += task.getEffortEstimate();
        }

        return totalEffort;
    }

    // Method to count unknown tasks
    private static int countUnknownTasks(Map<String, Task> taskMap) {
        int count = 0;

        // Count tasks without subtasks and without effort estimates
        for (Task task : taskMap.values()) {
            if (task.getSubTasks().isEmpty() && task.getEffortEstimate() == 0) {
                count++;
            }
        }

        return count;
    }
}
