package com.otc.api.model;


public class SearchRequest {
    private String query = "";

    public SearchRequest() {}
    public SearchRequest(String query) {
        this.query = query;
    }
    public String getQuery() { return this.query; }
    public void setQuery(String query) { this.query = query; }
}
