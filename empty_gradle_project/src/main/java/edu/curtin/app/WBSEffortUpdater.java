package edu.curtin.app;
import java.util.Map;

public class WBSEffortUpdater {
    // Method to record the new effort estimate within the WBS
    public static void recordNewEffort(Map<String, Task> taskMap, String taskId, int newEffort) {
        // Find the task by ID
        Task task = taskMap.get(taskId);
        if (task != null) {
            // Update the effort estimate for the identified task
            task.setEffortEstimate(newEffort);

            // Update effort estimates for subtasks recursively
            updateSubtaskEfforts(task, newEffort, taskMap);
        } else {
            System.out.println("Task with ID " + taskId + " not found.");
        }
    }

    // Method to update effort estimates for subtasks recursively
    private static void updateSubtaskEfforts(Task task, int newEffort, Map<String, Task> taskMap) {
        for (Task subTask : task.getSubTasks()) {
            subTask.setEffortEstimate(newEffort);
            taskMap.put(subTask.getId(), subTask); // Update the task in the task map
            updateSubtaskEfforts(subTask, newEffort, taskMap);
        }
    }
}
