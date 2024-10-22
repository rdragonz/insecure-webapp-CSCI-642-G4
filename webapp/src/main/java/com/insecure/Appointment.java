package com.insecure;

import java.util.Date;
import java.lang.String;

public class Appointment {
    private String description;
    private Date startTime;
    private Date endTime;

    public Appointment(String description, Date startTime, Date endTime) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }
}
