package com.otc.api.features.dashboard.repository;

import com.otc.api.features.dashboard.model.ChartData;
import com.otc.api.features.dashboard.model.DistrictReportItem;
import com.otc.api.features.dashboard.model.SummaryMetrics;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class DashboardRepositoryImpl implements DashboardRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall getSummaryCall;
    private SimpleJdbcCall getChartCall;
    private SimpleJdbcCall getDistrictReportCall;

    public DashboardRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    void init() {
        this.getSummaryCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("proc_HomeScreenCard");

        this.getChartCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("Proc_OTCHomeDashboardChart")
                .returningResultSet("chartData", (rs, rowNum) -> new ChartData(
                        rs.getString("Name"),
                        rs.getDouble("Value")
                ));

        this.getDistrictReportCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("usp_GetHomeDepartmentReportDistrictWise")
                .returningResultSet("districtReport", (rs, rowNum) -> new DistrictReportItem(
                        rs.getString("District Name"),
                        rs.getInt("Total"),
                        rs.getInt("Completed"),
                        rs.getInt("Reset and Completed"),
                        rs.getInt("Skipped"),
                        rs.getString("DistrictCode")
                ));
    }

    @Override
    public SummaryMetrics getSummaryMetrics(String date, String username) {
        Map<String, Object> params = Map.of(
                "Date", date,
                "Username", username
        );
        Map<String, Object> result = getSummaryCall.execute(params);
        
        // proc_HomeScreenCard returns fields directly in the first result set or as out params?
        // Legacy code showed it returning a result set with Total, Completed, Reset, Skipped.
        // SimpleJdbcCall handles the first result set as part of the output map if not specified.
        // But usually it's better to explicitly map it.
        
        List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("#result-set-1");
        if (list != null && !list.isEmpty()) {
            Map<String, Object> row = list.get(0);
            return new SummaryMetrics(
                    (int) row.getOrDefault("Total", 0),
                    (int) row.getOrDefault("Completed", 0),
                    (int) row.getOrDefault("Reset", 0),
                    (int) row.getOrDefault("Skipped", 0)
            );
        }
        return new SummaryMetrics(0, 0, 0, 0);
    }

    @Override
    public List<ChartData> getChartData(String date, String username) {
        Map<String, Object> params = Map.of(
                "Date", date,
                "Username", username
        );
        Map<String, Object> result = getChartCall.execute(params);
        return (List<ChartData>) result.getOrDefault("chartData", Collections.emptyList());
    }

    @Override
    public List<DistrictReportItem> getDistrictReport(String date, String username) {
        Map<String, Object> params = Map.of(
                "Date", date,
                "UserName", username
        );
        Map<String, Object> result = getDistrictReportCall.execute(params);
        return (List<DistrictReportItem>) result.getOrDefault("districtReport", Collections.emptyList());
    }
}
