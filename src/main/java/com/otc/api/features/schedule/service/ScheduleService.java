package com.otc.api.features.schedule.service;

import com.otc.api.features.schedule.model.request.*;
import com.otc.api.features.schedule.model.response.*;
import com.otc.api.model.ActivityType;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ScheduleService {
    List<ScheduleListItem> getScheduleList(ScheduleListRequest request);
    boolean insertSchedule(ScheduleInsertRequest request);
    boolean updateSchedule(ScheduleUpdateRequest request);
    boolean deleteSchedule(ScheduleDeleteRequest request);
    List<ActivityType> getActivityTypes();

    BulkUploadResponse processBulkUpload(MultipartFile file, String username);
    byte[] generateTemplate();
}
