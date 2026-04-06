package com.otc.api.features.master.model;


import java.time.LocalDateTime;

public class AtmMaster {
    private String atmId = "";
    private String aliasAtmId = "";
    private String bank = "";
    private String siteId = "";
    private String site = "";
    private String address = "";
    private String city = "";
    private String state = "";
    private String pincode = "";
    private String region = "";
    private String location = "";
    private LocalDateTime installDate;
    private String atmCategory = "";
    private String model = "";
    private String loiCode = "";
    private String keyNumber = "";
    private String serialNo = "";
    private String comments = "";
    private String atmStatus = "";
    private String atmType = "";
    private String franchise = "";
    private String latitude = "";
    private String longitude = "";
    private String zom = "";
    private String custodian1 = "";
    private String custodian2 = "";
    private String custodian3 = "";
    private String routeKey = "";
    private boolean isActive = true;

    public AtmMaster() {}
    public AtmMaster(String atmId, String aliasAtmId, String bank, String siteId, String site, String address, String city, String state, String pincode, String region, String location, LocalDateTime installDate, String atmCategory, String model, String loiCode, String keyNumber, String serialNo, String comments, String atmStatus, String atmType, String franchise, String latitude, String longitude, String zom, String custodian1, String custodian2, String custodian3, String routeKey, boolean isActive) {
        this.atmId = atmId;
        this.aliasAtmId = aliasAtmId;
        this.bank = bank;
        this.siteId = siteId;
        this.site = site;
        this.address = address;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.region = region;
        this.location = location;
        this.installDate = installDate;
        this.atmCategory = atmCategory;
        this.model = model;
        this.loiCode = loiCode;
        this.keyNumber = keyNumber;
        this.serialNo = serialNo;
        this.comments = comments;
        this.atmStatus = atmStatus;
        this.atmType = atmType;
        this.franchise = franchise;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zom = zom;
        this.custodian1 = custodian1;
        this.custodian2 = custodian2;
        this.custodian3 = custodian3;
        this.routeKey = routeKey;
        this.isActive = isActive;
    }
    public String getAtmId() { return this.atmId; }
    public void setAtmId(String atmId) { this.atmId = atmId; }
    public String getAliasAtmId() { return this.aliasAtmId; }
    public void setAliasAtmId(String aliasAtmId) { this.aliasAtmId = aliasAtmId; }
    public String getBank() { return this.bank; }
    public void setBank(String bank) { this.bank = bank; }
    public String getSiteId() { return this.siteId; }
    public void setSiteId(String siteId) { this.siteId = siteId; }
    public String getSite() { return this.site; }
    public void setSite(String site) { this.site = site; }
    public String getAddress() { return this.address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return this.city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return this.state; }
    public void setState(String state) { this.state = state; }
    public String getPincode() { return this.pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }
    public String getRegion() { return this.region; }
    public void setRegion(String region) { this.region = region; }
    public String getLocation() { return this.location; }
    public void setLocation(String location) { this.location = location; }
    public LocalDateTime getInstallDate() { return this.installDate; }
    public void setInstallDate(LocalDateTime installDate) { this.installDate = installDate; }
    public String getAtmCategory() { return this.atmCategory; }
    public void setAtmCategory(String atmCategory) { this.atmCategory = atmCategory; }
    public String getModel() { return this.model; }
    public void setModel(String model) { this.model = model; }
    public String getLoiCode() { return this.loiCode; }
    public void setLoiCode(String loiCode) { this.loiCode = loiCode; }
    public String getKeyNumber() { return this.keyNumber; }
    public void setKeyNumber(String keyNumber) { this.keyNumber = keyNumber; }
    public String getSerialNo() { return this.serialNo; }
    public void setSerialNo(String serialNo) { this.serialNo = serialNo; }
    public String getComments() { return this.comments; }
    public void setComments(String comments) { this.comments = comments; }
    public String getAtmStatus() { return this.atmStatus; }
    public void setAtmStatus(String atmStatus) { this.atmStatus = atmStatus; }
    public String getAtmType() { return this.atmType; }
    public void setAtmType(String atmType) { this.atmType = atmType; }
    public String getFranchise() { return this.franchise; }
    public void setFranchise(String franchise) { this.franchise = franchise; }
    public String getLatitude() { return this.latitude; }
    public void setLatitude(String latitude) { this.latitude = latitude; }
    public String getLongitude() { return this.longitude; }
    public void setLongitude(String longitude) { this.longitude = longitude; }
    public String getZom() { return this.zom; }
    public void setZom(String zom) { this.zom = zom; }
    public String getCustodian1() { return this.custodian1; }
    public void setCustodian1(String custodian1) { this.custodian1 = custodian1; }
    public String getCustodian2() { return this.custodian2; }
    public void setCustodian2(String custodian2) { this.custodian2 = custodian2; }
    public String getCustodian3() { return this.custodian3; }
    public void setCustodian3(String custodian3) { this.custodian3 = custodian3; }
    public String getRouteKey() { return this.routeKey; }
    public void setRouteKey(String routeKey) { this.routeKey = routeKey; }
    public boolean isIsActive() { return this.isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
