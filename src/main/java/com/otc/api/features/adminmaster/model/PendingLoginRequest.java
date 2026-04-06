package com.otc.api.features.adminmaster.model;

import java.time.LocalDateTime;

public class PendingLoginRequest {
    private int id;
    private String username = "";
    private String custodianOrZomName = "";
    private LocalDateTime requestDate;
    private String requestFor = "";
    private String mobileInfo = "";
    private String comments = "";
    private String status = "Pending";

    public PendingLoginRequest() {}
    public PendingLoginRequest(int id, String username, String custodianOrZomName, LocalDateTime requestDate, String requestFor, String mobileInfo, String comments, String status) {
        this.id = id;
        this.username = username;
        this.custodianOrZomName = custodianOrZomName;
        this.requestDate = requestDate;
        this.requestFor = requestFor;
        this.mobileInfo = mobileInfo;
        this.comments = comments;
        this.status = status;
    }
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }
    public String getCustodianOrZomName() { return this.custodianOrZomName; }
    public void setCustodianOrZomName(String custodianOrZomName) { this.custodianOrZomName = custodianOrZomName; }
    public LocalDateTime getRequestDate() { return this.requestDate; }
    public void setRequestDate(LocalDateTime requestDate) { this.requestDate = requestDate; }
    public String getRequestFor() { return this.requestFor; }
    public void setRequestFor(String requestFor) { this.requestFor = requestFor; }
    public String getMobileInfo() { return this.mobileInfo; }
    public void setMobileInfo(String mobileInfo) { this.mobileInfo = mobileInfo; }
    public String getComments() { return this.comments; }
    public void setComments(String comments) { this.comments = comments; }
    public String getStatus() { return this.status; }
    public void setStatus(String status) { this.status = status; }
}
