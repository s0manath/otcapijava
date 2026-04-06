package com.otc.api.features.schedule.model.request;

public record ScheduleListRequest(
    String fromDate,
    String toDate,
    String username,
    String searchField,
    String startWith
) {
    public ScheduleListRequest {
        if (username == null) username = "admin";
    }
}
