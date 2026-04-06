package com.otc.api.features.schedule.service.impl;

import com.otc.api.features.schedule.model.request.*;
import com.otc.api.features.schedule.model.response.*;
import com.otc.api.features.schedule.repository.ScheduleRepository;
import com.otc.api.model.ActivityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import com.otc.api.features.schedule.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleListItem> getScheduleList(ScheduleListRequest request) {
        try {
            return scheduleRepository.getScheduleList(request);
        } catch (Exception e) {
            logger.error("getScheduleList failed", e);
            throw new RuntimeException("Unable to fetch schedules", e);
        }
    }

    public boolean insertSchedule(ScheduleInsertRequest request) {
        try {
            return scheduleRepository.insertSchedule(request);
        } catch (Exception e) {
            logger.error("insertSchedule failed", e);
            return false;
        }
    }

    public boolean updateSchedule(ScheduleUpdateRequest request) {
        try {
            return scheduleRepository.updateSchedule(request);
        } catch (Exception e) {
            logger.error("updateSchedule failed", e);
            return false;
        }
    }

    public boolean deleteSchedule(ScheduleDeleteRequest request) {
        try {
            return scheduleRepository.deleteSchedule(request);
        } catch (Exception e) {
            logger.error("deleteSchedule failed", e);
            return false;
        }
    }

    public List<ActivityType> getActivityTypes() {
        try {
            return scheduleRepository.getActivityTypes();
        } catch (Exception e) {
            logger.error("getActivityTypes failed", e);
            return Collections.emptyList();
        }
    }
}