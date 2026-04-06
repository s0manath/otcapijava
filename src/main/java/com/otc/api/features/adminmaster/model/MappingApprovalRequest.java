package com.otc.api.features.adminmaster.model;


public class MappingApprovalRequest {
    private int requestId;
    private String comments = "";
    private boolean isApproved;

    public MappingApprovalRequest() {}
    public MappingApprovalRequest(int requestId, String comments, boolean isApproved) {
        this.requestId = requestId;
        this.comments = comments;
        this.isApproved = isApproved;
    }
    public int getRequestId() { return this.requestId; }
    public void setRequestId(int requestId) { this.requestId = requestId; }
    public String getComments() { return this.comments; }
    public void setComments(String comments) { this.comments = comments; }
    public boolean isIsApproved() { return this.isApproved; }
    public void setIsApproved(boolean isApproved) { this.isApproved = isApproved; }
}
