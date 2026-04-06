package com.otc.api.features.adminmaster.model;

import java.time.LocalDateTime;

public class CustodianLoginMapping {
    private String username = "";
    private String fullName = "";
    private String password = "";
    private String custodianCode = "";
    private String custodianEmail = "";
    private LocalDateTime createdDate = LocalDateTime.now();

    public CustodianLoginMapping() {}
    public CustodianLoginMapping(String username, String fullName, String password, String custodianCode, String custodianEmail, LocalDateTime createdDate) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.custodianCode = custodianCode;
        this.custodianEmail = custodianEmail;
        this.createdDate = createdDate;
    }
    public String getUsername() { return this.username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return this.fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPassword() { return this.password; }
    public void setPassword(String password) { this.password = password; }
    public String getCustodianCode() { return this.custodianCode; }
    public void setCustodianCode(String custodianCode) { this.custodianCode = custodianCode; }
    public String getCustodianEmail() { return this.custodianEmail; }
    public void setCustodianEmail(String custodianEmail) { this.custodianEmail = custodianEmail; }
    public LocalDateTime getCreatedDate() { return this.createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}
