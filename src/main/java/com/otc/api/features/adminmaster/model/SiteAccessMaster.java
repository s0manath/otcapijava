package com.otc.api.features.adminmaster.model;

import java.util.Map;
import java.util.HashMap;

public class SiteAccessMaster {
    private int id;
    private String siteId = "";
    private String siteName = "";
    private String accessTimeFrom = "";
    private String accessTimeTo = "";
    private Map<String, Boolean> availableDays = new HashMap<>();
    private boolean isActive = true;

    public SiteAccessMaster() {}
    public SiteAccessMaster(int id, String siteId, String siteName, String accessTimeFrom, String accessTimeTo, Map<String, Boolean> availableDays, boolean isActive) {
        this.id = id;
        this.siteId = siteId;
        this.siteName = siteName;
        this.accessTimeFrom = accessTimeFrom;
        this.accessTimeTo = accessTimeTo;
        this.availableDays = availableDays;
        this.isActive = isActive;
    }
    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }
    public String getSiteId() { return this.siteId; }
    public void setSiteId(String siteId) { this.siteId = siteId; }
    public String getSiteName() { return this.siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }
    public String getAccessTimeFrom() { return this.accessTimeFrom; }
    public void setAccessTimeFrom(String accessTimeFrom) { this.accessTimeFrom = accessTimeFrom; }
    public String getAccessTimeTo() { return this.accessTimeTo; }
    public void setAccessTimeTo(String accessTimeTo) { this.accessTimeTo = accessTimeTo; }
    public Map<String, Boolean> getAvailableDays() { return this.availableDays; }
    public void setAvailableDays(Map<String, Boolean> availableDays) { this.availableDays = availableDays; }
    public boolean isIsActive() { return this.isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
}
