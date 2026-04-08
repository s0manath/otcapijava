package com.otc.api.features.schedule.model.response;


public record ScheduleListItem(
    String scheduleId,
    String atmId,
    String activityType,
    String scheduleDate,
    String createdDate,
    String createdBy,
    String comment

) {}
