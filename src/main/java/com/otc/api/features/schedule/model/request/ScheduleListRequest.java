package com.otc.api.features.schedule.model.request;

public class ScheduleListRequest {
    private String fromDate = "";
    private String toDate = "";
    private String username = "admin";
    private String searchField;
    private String startWith;

    // Default constructor
    public ScheduleListRequest() {}

    // Getters and Setters
    public String getFromDate() { return fromDate; }
    public void setFromDate(String fromDate) { this.fromDate = fromDate; }

    public String getToDate() { return toDate; }
    public void setToDate(String toDate) { this.toDate = toDate; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSearchField() { return searchField; }
    public void setSearchField(String searchField) { this.searchField = searchField; }

    public String getStartWith() { return startWith; }
    public void setStartWith(String startWith) { this.startWith = startWith; }
}

