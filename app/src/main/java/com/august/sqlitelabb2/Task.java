package com.august.sqlitelabb2;

/**
 * Created by mrx on 2018-02-05.
 */

public class Task {

    private int id;
    private String title;
    private String deadline;
    private Boolean done;
    private String description;
    private String category;
    private String priority;




    public Task() {}

    public Task(String title, String deadline, String description, String category, String priority) {
        this.title = title;
        this.deadline = deadline;
        done = false;
        this.description = description;
        this.category = category;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {

        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDeadline() {
        return deadline;
    }
    public Boolean getDone() {
        return done;
    }
    public void setDone(Boolean done) {
        this.done = done;
    }
    public String getDescription() {
        return description;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }
    public String getCategory() {

        return category;
    }
    public String getPriority() {
        return priority;
    }
}
