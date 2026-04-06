package com.otc.api.features.adminmaster.model;


public class RouteMasterAdmin {
    private int id;
    private String routeKey = "";
    private String custodianId = "";
    private String touchKeyId = "";
    private String state = "";
    private String district = "";
    private String zom = "";
    private String franchise = "";
    private String mobileNumber = "";
    private String croType = "";
    private boolean isActive = true;

    public RouteMasterAdmin() {}
    public RouteMasterAdmin(int id, String routeKey, String custodianId, String touchKeyId, String state, String district, String zom, String franchise, String mobileNumber, String croType, boolean isActive) {
        this.id = id;
        this.routeKey = routeKey;
        this.custodianId = custodianId;
        this.touchKeyId = touchKeyId;
        this.state = state;
        this.district = district;
        this.zom = zom;
        this.franchise = franchise;
        this.mobileNumber = mobileNumber;
        this.croType = croType;
        this.isActive = isActive;
    }
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getRouteKey() { return this.routeKey; }
    public void setRouteKey(String routeKey) { this.routeKey = routeKey; }
    public String getCustodianId() { return this.custodianId; }
    public void setCustodianId(String custodianId) { this.custodianId = custodianId; }
    public String getTouchKeyId() { return this.touchKeyId; }
    public void setTouchKeyId(String touchKeyId) { this.touchKeyId = touchKeyId; }
    public String getState() { return this.state; }
    public void setState(String state) { this.state = state; }
    public String getDistrict() { return this.district; }
    public void setDistrict(String district) { this.district = district; }
    public String getZom() { return this.zom; }
    public void setZom(String zom) { this.zom = zom; }
    public String getFranchise() { return this.franchise; }
    public void setFranchise(String franchise) { this.franchise = franchise; }
    public String getMobileNumber() { return this.mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getCroType() { return this.croType; }
    public void setCroType(String croType) { this.croType = croType; }
    public boolean isIsActive() { return this.isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
