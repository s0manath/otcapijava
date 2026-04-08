package com.otc.api.features.schedule.controller;

import com.otc.api.features.schedule.model.request.*;
import com.otc.api.features.schedule.model.response.*;
import com.otc.api.features.schedule.service.ScheduleService;
import com.otc.api.model.ActivityType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    //Completed fetching list of schedule
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

    @PostMapping("/activity-types")
    public ResponseEntity<List<ActivityType>> getActivityTypes() {
        return ResponseEntity.ok(scheduleService.getActivityTypes());
    }

    @PostMapping("/bulk-upload")
    public ResponseEntity<BulkUploadResponse> bulkUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "username", defaultValue = "admin") String username) {
        return ResponseEntity.ok(scheduleService.processBulkUpload(file, username));
    }

    @GetMapping("/download-template")
    public ResponseEntity<Resource> downloadTemplate() {
        byte[] data = scheduleService.generateTemplate();
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Schedule_Template.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .contentLength(data.length)
                .body(resource);
    }

    public record MessageResponse(String message) {}
}
