package com.otc.api.features.dashboard.controller;

import com.otc.api.features.dashboard.model.ChartData;
import com.otc.api.features.dashboard.model.DistrictReportItem;
import com.otc.api.features.dashboard.model.SummaryMetrics;
import com.otc.api.features.dashboard.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryMetrics> getSummary(
            @RequestParam String date,
            @RequestParam(defaultValue = "admin") String username) {
        return ResponseEntity.ok(dashboardService.getSummaryMetrics(date, username));
    }

    @GetMapping("/chart")
    public ResponseEntity<List<ChartData>> getChartData(
            @RequestParam String date,
            @RequestParam(defaultValue = "admin") String username) {
        return ResponseEntity.ok(dashboardService.getChartData(date, username));
    }

    @GetMapping("/district-report")
    public ResponseEntity<List<DistrictReportItem>> getDistrictReport(
            @RequestParam String date,
            @RequestParam(defaultValue = "admin") String username) {
        return ResponseEntity.ok(dashboardService.getDistrictReport(date, username));
    }
}
