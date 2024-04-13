package edu.curtin.app;

public class WBSDisplayer {
    // Method to display WBS and effort summary
    public static void displayWBS(Task rootTask) {
        // Display the WBS recursively
        System.out.println("Work Breakdown Structure:");
        displayTask(rootTask, 0);

        // Calculate and display effort summary
        int totalKnownEffort = calculateTotalKnownEffort(rootTask);
        int unknownTasksCount = countUnknownTasks(rootTask);
        System.out.println("Total known effort = " + totalKnownEffort);
        System.out.println("Unknown tasks = " + unknownTasksCount);
    }

    // Recursive method to display tasks and their subtasks with proper indentation
    private static void displayTask(Task task, int depth) {
        // Display task details
        if (!task.getId().isEmpty()) {
            // Indent based on the depth
            for (int i = 0; i < depth; i++) {
                System.out.print("  ");
            }
            System.out.println(task.getId() + ": " + task.getDescription() + ", effort = " + task.getEffortEstimate());
        } else {
            // For root task, no indentation needed
            System.out.println("Root: " + task.getDescription() + ", effort = " + task.getEffortEstimate());
        }

        // Display subtasks recursively
        for (Task subTask : task.getSubTasks()) {
            displayTask(subTask, depth + 1);
        }
    }

    // Method to calculate total known effort
    private static int calculateTotalKnownEffort(Task task) {
        int totalEffort = task.getEffortEstimate();

        // Calculate effort for subtasks recursively
        for (Task subTask : task.getSubTasks()) {
            totalEffort += calculateTotalKnownEffort(subTask);
        }

        return totalEffort;
    }

    // Method to count unknown tasks
    private static int countUnknownTasks(Task task) {
        int count = 0;

        // Check if the task has subtasks and effort estimate
        if (task.getSubTasks().isEmpty() && task.getEffortEstimate() == 0) {
            count++;
        }

        // Count unknown tasks for subtasks recursively
        for (Task subTask : task.getSubTasks()) {
            count += countUnknownTasks(subTask);
        }

        return count;
    }

    // Method to estimate effort
    public static void estimateEffort(Task rootTask) {
        int totalEffort = calculateTotalEffort(rootTask);
        System.out.println("Total Effort Estimate: " + totalEffort);
    }

    // Recursive method to calculate total effort
    private static int calculateTotalEffort(Task task) {
        int totalEffort = task.getEffortEstimate();

        // Calculate effort for subtasks recursively
        for (Task subTask : task.getSubTasks()) {
            totalEffort += calculateTotalEffort(subTask);
        }

        return totalEffort;
    }
}
