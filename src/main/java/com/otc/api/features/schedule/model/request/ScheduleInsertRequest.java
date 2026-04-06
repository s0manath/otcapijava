package com.otc.api.features.schedule.model.request;

public record ScheduleInsertRequest(
    String atmId,
    String activityType,
    String scheduleDate,
    String username,
    String bulkScheduleInfo
) {
    public ScheduleInsertRequest {
        if (username == null) username = "admin";
    }
}
