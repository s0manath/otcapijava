package com.otc.api.features.schedule.model.request;

public record ScheduleUpdateRequest(
    String scheduleId,
    String atmId,
    String activityType,
    String scheduleDate,
    String username,
    String comment
) {
    public ScheduleUpdateRequest {
        if (username == null) username = "admin";
    }
}
