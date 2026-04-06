package com.otc.api.features.route.model.response;

public record RouteListItem(
    String id,
    String routeId,
    String atmId,
    String activityType,
    String scheduleDate,
    String region,
    String district,
    String franchise,
    String zom,
    String status,
    String routeKey,
    String custodian1,
    String custodian2,
    String completedDate
) {}
