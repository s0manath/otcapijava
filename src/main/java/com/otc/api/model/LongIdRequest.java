package com.otc.api.model;


public class LongIdRequest {
    private long id;

    public LongIdRequest() {}
    public LongIdRequest(long id) {
        this.id = id;
    }
    public long getId() { return this.id; }
    public void setId(long id) { this.id = id; }
}
