package com.otc.api.features.adminmaster.model;


public class AdminMasterSearchRequest {
    private String query;
    private Boolean isActive;

    public AdminMasterSearchRequest() {}
    public AdminMasterSearchRequest(String query, Boolean isActive) {
        this.query = query;
        this.isActive = isActive;
    }
    public String getQuery() { return this.query; }
    public void setQuery(String query) { this.query = query; }
    public Boolean getIsActive() { return this.isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
