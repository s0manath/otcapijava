package com.otc.api.features.dashboard.service.impl;

import com.otc.api.features.dashboard.model.ChartData;
import com.otc.api.features.dashboard.model.DistrictReportItem;
import com.otc.api.features.dashboard.model.SummaryMetrics;
import com.otc.api.features.dashboard.repository.DashboardRepository;
import com.otc.api.features.dashboard.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    public DashboardServiceImpl(DashboardRepository dashboardRepository) {
        this.dashboardRepository = dashboardRepository;
    }

    @Override
    public SummaryMetrics getSummaryMetrics(String date, String username) {
        return dashboardRepository.getSummaryMetrics(date, username);
    }

    @Override
    public List<ChartData> getChartData(String date, String username) {
        return dashboardRepository.getChartData(date, username);
    }

    @Override
    public List<DistrictReportItem> getDistrictReport(String date, String username) {
        return dashboardRepository.getDistrictReport(date, username);
    }
}
