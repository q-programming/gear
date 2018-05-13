package com.qprogramming.activtytracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Activity {
    @JsonIgnore
    private ZonedDateTime start;
    private String startTime;
    @JsonIgnore
    private ZonedDateTime end;
    private String endTime;
    private long minutes;
    private Type type;

    public Activity() {
    }

    public Activity(Type type) {
        this.type = type;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public double getHours() {
        return ActivityUtils.getHours(minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        if (!start.equals(activity.start)) {
            return false;
        }
        if (end != null ? !end.equals(activity.end) : activity.end != null) {
            return false;
        }
        return type == activity.type;
    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "type=" + type +
                ", startTime='" + start + '\'' +
                ", endTime='" + end + '\'' +
                '}';
    }
}
