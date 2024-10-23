package com.example.calendar.models;

public class Appointment {
    private int id;
    private String date;
    private String time;
    private String description;

    public Appointment(int id, String date, String time, String description) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getDescription() { return description; }
}
