package com.otc.api.features.route.model.response;

public record RouteListItem(
    String id,
    String routeId,
    String atmId,
    String activityType,
    String scheduleDate,
    String region,
    String franchiseName,
    String zom,
    String status,
    String routeKey,
    String custodian1,
    String custodian2,
    String districtName,
    String croType

) {}
