package com.otc.api.features.dashboard.repository;

import com.otc.api.features.dashboard.model.ChartData;
import com.otc.api.features.dashboard.model.DistrictReportItem;
import com.otc.api.features.dashboard.model.SummaryMetrics;

import java.util.List;

public interface DashboardRepository {
    SummaryMetrics getSummaryMetrics(String date, String username);
    List<ChartData> getChartData(String date, String username);
    List<DistrictReportItem> getDistrictReport(String date, String username);
}
