package com.otc.api.features.schedule.repository;

import com.otc.api.features.schedule.model.request.*;
import com.otc.api.features.schedule.model.response.*;
import com.otc.api.model.ActivityType;
import java.util.List;

public interface ScheduleRepositoryCustom {
    List<ScheduleListItem> getScheduleList(ScheduleListRequest request);
    boolean insertSchedule(ScheduleInsertRequest request);
    boolean updateSchedule(ScheduleUpdateRequest request);
    boolean deleteSchedule(ScheduleDeleteRequest request);
    List<ActivityType> getActivityTypes();
}
