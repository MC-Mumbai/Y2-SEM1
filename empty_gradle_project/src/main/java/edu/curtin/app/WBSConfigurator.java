package edu.curtin.app;

import java.util.Scanner;

public class WBSConfigurator {
    // Method to configure settings
    public static void configureSettings(Task rootTask) {
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
    private static Task findTaskById(Task task, String taskId) {
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
