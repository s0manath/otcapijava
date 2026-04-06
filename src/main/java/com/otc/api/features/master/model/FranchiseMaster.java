package com.otc.api.features.master.model;


public class FranchiseMaster {
    private int id;
    private String franchiseName = "";
    private String mobileNumber = "";
    private String emailId = "";
    private String sapCode = "";
    private boolean secondaryCustodianRequire = false;
    private Integer stateId;
    private String stateName = "";
    private Integer districtId;
    private String districtName = "";
    private boolean isActive = true;

    public FranchiseMaster() {}
    public FranchiseMaster(int id, String franchiseName, String mobileNumber, String emailId, String sapCode, boolean secondaryCustodianRequire, Integer stateId, String stateName, Integer districtId, String districtName, boolean isActive) {
        this.id = id;
        this.franchiseName = franchiseName;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.sapCode = sapCode;
        this.secondaryCustodianRequire = secondaryCustodianRequire;
        this.stateId = stateId;
        this.stateName = stateName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.isActive = isActive;
    }
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getFranchiseName() { return this.franchiseName; }
    public void setFranchiseName(String franchiseName) { this.franchiseName = franchiseName; }
    public String getMobileNumber() { return this.mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getEmailId() { return this.emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }
    public String getSapCode() { return this.sapCode; }
    public void setSapCode(String sapCode) { this.sapCode = sapCode; }
    public boolean isSecondaryCustodianRequire() { return this.secondaryCustodianRequire; }
    public void setSecondaryCustodianRequire(boolean secondaryCustodianRequire) { this.secondaryCustodianRequire = secondaryCustodianRequire; }
    public Integer getStateId() { return this.stateId; }
    public void setStateId(Integer stateId) { this.stateId = stateId; }
    public String getStateName() { return this.stateName; }
    public void setStateName(String stateName) { this.stateName = stateName; }
    public Integer getDistrictId() { return this.districtId; }
    public void setDistrictId(Integer districtId) { this.districtId = districtId; }
    public String getDistrictName() { return this.districtName; }
    public void setDistrictName(String districtName) { this.districtName = districtName; }
    public boolean isIsActive() { return this.isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
