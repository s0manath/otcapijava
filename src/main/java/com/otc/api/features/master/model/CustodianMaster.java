package com.otc.api.features.master.model;


import java.time.LocalDateTime;

public class CustodianMaster {
    private int id;
    private String custodianName = "";
    private String mobileNumber = "";
    private String emailId = "";
    private Integer locationId;
    private String locationName = "";
    private Integer zomId;
    private String zomName = "";
    private Integer franchiseId;
    private String franchiseName = "";
    private Integer routeKeyId;
    private String routeKeyName = "";
    private String touchKeyId = "";
    private String custodianCode = "";
    private LocalDateTime accessFrom;
    private LocalDateTime accessTo;
    private String profileImage = "";
    private boolean isActive = true;

    public CustodianMaster() {}
    public CustodianMaster(int id, String custodianName, String mobileNumber, String emailId, Integer locationId, String locationName, Integer zomId, String zomName, Integer franchiseId, String franchiseName, Integer routeKeyId, String routeKeyName, String touchKeyId, String custodianCode, LocalDateTime accessFrom, LocalDateTime accessTo, String profileImage, boolean isActive) {
        this.id = id;
        this.custodianName = custodianName;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.locationId = locationId;
        this.locationName = locationName;
        this.zomId = zomId;
        this.zomName = zomName;
        this.franchiseId = franchiseId;
        this.franchiseName = franchiseName;
        this.routeKeyId = routeKeyId;
        this.routeKeyName = routeKeyName;
        this.touchKeyId = touchKeyId;
        this.custodianCode = custodianCode;
        this.accessFrom = accessFrom;
        this.accessTo = accessTo;
        this.profileImage = profileImage;
        this.isActive = isActive;
    }
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getCustodianName() { return this.custodianName; }
    public void setCustodianName(String custodianName) { this.custodianName = custodianName; }
    public String getMobileNumber() { return this.mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getEmailId() { return this.emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
    public Integer getLocationId() { return this.locationId; }
    public void setLocationId(Integer locationId) { this.locationId = locationId; }
    public String getLocationName() { return this.locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public Integer getZomId() { return this.zomId; }
    public void setZomId(Integer zomId) { this.zomId = zomId; }
    public String getZomName() { return this.zomName; }
    public void setZomName(String zomName) { this.zomName = zomName; }
    public Integer getFranchiseId() { return this.franchiseId; }
    public void setFranchiseId(Integer franchiseId) { this.franchiseId = franchiseId; }
    public String getFranchiseName() { return this.franchiseName; }
    public void setFranchiseName(String franchiseName) { this.franchiseName = franchiseName; }
    public Integer getRouteKeyId() { return this.routeKeyId; }
    public void setRouteKeyId(Integer routeKeyId) { this.routeKeyId = routeKeyId; }
    public String getRouteKeyName() { return this.routeKeyName; }
    public void setRouteKeyName(String routeKeyName) { this.routeKeyName = routeKeyName; }
    public String getTouchKeyId() { return this.touchKeyId; }
    public void setTouchKeyId(String touchKeyId) { this.touchKeyId = touchKeyId; }
    public String getCustodianCode() { return this.custodianCode; }
    public void setCustodianCode(String custodianCode) { this.custodianCode = custodianCode; }
    public LocalDateTime getAccessFrom() { return this.accessFrom; }
    public void setAccessFrom(LocalDateTime accessFrom) { this.accessFrom = accessFrom; }
    public LocalDateTime getAccessTo() { return this.accessTo; }
    public void setAccessTo(LocalDateTime accessTo) { this.accessTo = accessTo; }
    public String getProfileImage() { return this.profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }
    public boolean isIsActive() { return this.isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
