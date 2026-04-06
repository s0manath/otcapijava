package com.otc.api.features.adminmaster.model;


public class LocationMaster {
    private int id;
    private String locationName = "";
    private String regionCode = "";
    private String regionName = "";
    private boolean isActive = true;

    public LocationMaster() {}
    public LocationMaster(int id, String locationName, String regionCode, String regionName, boolean isActive) {
        this.id = id;
        this.locationName = locationName;
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.isActive = isActive;
    }
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getLocationName() { return this.locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public String getRegionCode() { return this.regionCode; }
    public void setRegionCode(String regionCode) { this.regionCode = regionCode; }
    public String getRegionName() { return this.regionName; }
    public void setRegionName(String regionName) { this.regionName = regionName; }
    public boolean isIsActive() { return this.isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
