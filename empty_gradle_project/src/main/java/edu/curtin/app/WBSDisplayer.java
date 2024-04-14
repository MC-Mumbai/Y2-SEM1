package edu.curtin.app;

import java.util.Scanner;

import java.util.Map;

public class WBSDisplayer {
    private static Scanner scanner = new Scanner(System.in);

    // Method to find a task by its ID
    public static Task findTaskById(Map<String, Task> taskMap, String taskId) {
        return taskMap.get(taskId);
    }

    // Method to check if a task with a given ID exists in the map
    public static boolean containsTask(Map<String, Task> taskMap, String taskId) {
        return taskMap.containsKey(taskId);
    }

    // Method to display task details
    public static void displayTask(Task task) {
        if (task != null) {
            System.out.println("Task: " + task.getDescription() + ", Effort Estimate: " + task.getEffortEstimate());
        } else {
            System.out.println("Task not found.");
        }
    }

    // Method to display task and its subtasks in the specified format
    private static void displayTaskAndSubtasksRecursive(Task task, String prefix) {
        // Display task details with prefix
        System.out.println(prefix + task.getId() + ": " + task.getDescription() + ", effort = " + task.getEffortEstimate());

        // Display subtasks recursively with an increased indent
        String subtaskPrefix = prefix + "  ";
        for (Task subTask : task.getSubTasks()) {
            displayTaskAndSubtasksRecursive(subTask, subtaskPrefix); 
        }
    }

    // Method to display task and its subtasks
    public static void displayTaskAndSubtasks(Map<String, Task> taskMap) {
        // Display root tasks and their subtasks
        for (Map.Entry<String, Task> entry : taskMap.entrySet()) {
            Task task = entry.getValue();
            if (task.getParentTaskID() == null) {
                displayTaskAndSubtasksRecursive(task, "");
            }
        }
    }
}
