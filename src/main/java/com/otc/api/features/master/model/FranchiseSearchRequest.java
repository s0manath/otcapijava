package com.otc.api.features.master.model;


public class FranchiseSearchRequest {
    private String filterField;
    private String filterValue;

    public FranchiseSearchRequest() {}
    public FranchiseSearchRequest(String filterField, String filterValue) {
        this.filterField = filterField;
        this.filterValue = filterValue;
    }
    public String getFilterField() { return this.filterField; }
    public void setFilterField(String filterField) { this.filterField = filterField; }
    public String getFilterValue() { return this.filterValue; }
    public void setFilterValue(String filterValue) { this.filterValue = filterValue; }
}
