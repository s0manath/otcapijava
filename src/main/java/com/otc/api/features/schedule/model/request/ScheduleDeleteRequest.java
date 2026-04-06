package com.otc.api.features.schedule.model.request;

public record ScheduleDeleteRequest(
    String id,
    String username
) {
    public ScheduleDeleteRequest {
        if (username == null) username = "admin";
    }
}
