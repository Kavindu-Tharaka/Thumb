package com.ktm.thumb_first;

public class Tasks {

    private int id;
    private String task;
    private String date;
    private String time;
    private String number;


    public Tasks(String task, String date, String time) {
        this.task = task;
        this.date = date;
        this.time = time;
    }

    public Tasks(int id, String task, String date, String time) {
        this.id = id;
        this.task = task;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
