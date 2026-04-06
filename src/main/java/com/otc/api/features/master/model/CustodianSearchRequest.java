package com.otc.api.features.master.model;


public class CustodianSearchRequest {
    private String field;
    private String startsWith;
    private String chkLocked;

    public CustodianSearchRequest() {}
    public CustodianSearchRequest(String field, String startsWith, String chkLocked) {
        this.field = field;
        this.startsWith = startsWith;
        this.chkLocked = chkLocked;
    }
    public String getField() { return this.field; }
    public void setField(String field) { this.field = field; }
    public String getStartsWith() { return this.startsWith; }
    public void setStartsWith(String startsWith) { this.startsWith = startsWith; }
    public String getChkLocked() { return this.chkLocked; }
    public void setChkLocked(String chkLocked) { this.chkLocked = chkLocked; }
}
