package com.otc.api.features.adminmaster.model;

import java.util.List;
import java.util.ArrayList;

public class BulkUpdateRouteKeyRequest {
    private List<String> atmIds = new ArrayList<>();
    private String routeKey = "";

    public BulkUpdateRouteKeyRequest() {}
    public BulkUpdateRouteKeyRequest(List<String> atmIds, String routeKey) {
        this.atmIds = atmIds;
        this.routeKey = routeKey;
    }
    public List<String> getAtmIds() { return this.atmIds; }
    public void setAtmIds(List<String> atmIds) { this.atmIds = atmIds; }
    public String getRouteKey() { return this.routeKey; }
    public void setRouteKey(String routeKey) { this.routeKey = routeKey; }
}
