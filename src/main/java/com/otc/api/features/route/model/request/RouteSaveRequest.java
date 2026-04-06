package com.otc.api.features.route.model.request;

public record RouteSaveRequest(
    String scheduleId,
    String routeConfigId,
    String atmId,
    String routeKey,
    String custodian1,
    String custodian2,
    String username,
    boolean updateAll
) {
    public RouteSaveRequest {
        if (username == null) username = "admin";
    }
}
