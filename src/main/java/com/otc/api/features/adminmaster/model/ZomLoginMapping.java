package com.otc.api.features.adminmaster.model;

import java.time.LocalDateTime;

public class ZomLoginMapping {
    private String username = "";
    private String fullName = "";
    private String password = "";
    private String zomCode = "";
    private String zomEmail = "";
    private LocalDateTime createdDate = LocalDateTime.now();

    public ZomLoginMapping() {}
    public ZomLoginMapping(String username, String fullName, String password, String zomCode, String zomEmail, LocalDateTime createdDate) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.zomCode = zomCode;
        this.zomEmail = zomEmail;
        this.createdDate = createdDate;
    }
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return this.fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }
    public String getZomCode() { return this.zomCode; }
    public void setZomCode(String zomCode) { this.zomCode = zomCode; }
    public String getZomEmail() { return this.zomEmail; }
    public void setZomEmail(String zomEmail) { this.zomEmail = zomEmail; }
    public LocalDateTime getCreatedDate() { return this.createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}
