package com.example.to_dolist;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable{
    String taskTitle;
    String Date;
    String time;
    int taskNum;
String priority;

    public Task(String taskTitle, String date, String time, int taskNum, String priority) {
        this.taskTitle = taskTitle;
        Date = date;
        this.time = time;
        this.taskNum = taskNum;

        this.priority = priority;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTaskNum() {
        return taskNum;
    }

    public void setTaskNum(int taskNum) {
        this.taskNum = taskNum;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskTitle='" + taskTitle + '\'' +
                ", Date='" + Date + '\'' +
                ", time='" + time + '\'' +
                ", taskNum=" + taskNum +
                ", priority=" + priority +
                '}';
    }
}
