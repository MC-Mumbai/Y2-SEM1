package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;

public class Task {
    private String id;
    private String description;
    private int effortEstimate;
    private String parentTaskID; // New field to store the ID of the parent task
    private List<Task> subTasks;

    // Constructor
    public Task(String id, String description, int effortEstimate, String parentTaskID) {
        this.id = id;
        this.description = description;
        this.effortEstimate = effortEstimate;
        this.parentTaskID = parentTaskID;
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

    public String getParentTaskID() {
        return parentTaskID;
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

    public void setParentTaskID(String parentTaskID) {
        this.parentTaskID = parentTaskID;
    }

    // Method to add subtask
    public void addSubTask(Task task) {
        subTasks.add(task);
    }
}
