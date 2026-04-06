package com.otc.api.features.route.model.request;

public record RouteListRequest(
    String fromDate,
    String toDate,
    String username,
    String region,
    String district,
    String franchise,
    String zom,
    String activityType,
    String status,
    String chkConfig,
    String searchField,
    String criteria,
    String searchValue
) {
    public RouteListRequest {
        if (username == null) username = "admin";
    }
}
