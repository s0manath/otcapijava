package com.otc.api.features.schedule.model.request;

public class ScheduleInsertRequest {
    private String atmId = "";
    private String activityType = "";
    private String scheduleDate = "";
    private String username = "admin";
    private String bulkScheduleInfo = "";
    private String Comment ="";

    // Default Constructor (Required by many frameworks like Jackson/Spring)
    public ScheduleInsertRequest() {}

    // Getters and Setters
    public String getAtmId() { return atmId; }
    public void setAtmId(String atmId) { this.atmId = atmId; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public String getScheduleDate() { return scheduleDate; }
    public void setScheduleDate(String scheduleDate) { this.scheduleDate = scheduleDate; }

    public String getUsername() { return username; }
    public void setUsername(String username) {
        this.username = (username == null) ? "admin" : username;
    }

    public String getBulkScheduleInfo() { return bulkScheduleInfo; }
    public void setBulkScheduleInfo(String bulkScheduleInfo) { this.bulkScheduleInfo = bulkScheduleInfo; }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

}

