package com.otc.api.features.schedule.controller;

import com.otc.api.features.schedule.model.request.*;
import com.otc.api.features.schedule.model.response.*;
import com.otc.api.features.schedule.service.ScheduleService;
import com.otc.api.model.ActivityType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<ScheduleListItem>> getList(@RequestBody ScheduleListRequest request) {
        return ResponseEntity.ok(scheduleService.getScheduleList(request));
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody ScheduleInsertRequest request) {
        if (scheduleService.insertSchedule(request)) {
            return ResponseEntity.ok(new MessageResponse("Schedule inserted successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Failed to insert schedule"));
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody ScheduleUpdateRequest request) {
        if (scheduleService.updateSchedule(request)) {
            return ResponseEntity.ok(new MessageResponse("Schedule updated successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Failed to update schedule"));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody ScheduleDeleteRequest request) {
        if (scheduleService.deleteSchedule(request)) {
            return ResponseEntity.ok(new MessageResponse("Schedule marked as deleted successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Deletion Failed! Remove mapping and configure route."));
    }

    @PostMapping("/activityTypes")
    public ResponseEntity<List<ActivityType>> getActivityTypes() {
        return ResponseEntity.ok(scheduleService.getActivityTypes());
    }

    public record MessageResponse(String message) {}
}
