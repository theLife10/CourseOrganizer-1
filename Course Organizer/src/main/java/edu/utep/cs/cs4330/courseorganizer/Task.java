package edu.utep.cs.cs4330.courseorganizer;

/**
 * @author Kenneth Ward & Jesus Garcia
 * @version 1.0
 * @since 5/8/2019
 */

/**
 * A class for storing and retrieving information related to tasks and assignments
 */
public class Task {
    private String task;
    private String course;
    private String dueDate;

    public Task (String task, String course, String dueDate){
        this.task = task;
        this.course = course;
        this.dueDate = dueDate;
    }

    public String getCourse() {
        return course;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getTask() {
        return task;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setTask(String task) {
        this.task = task;
    }
}
