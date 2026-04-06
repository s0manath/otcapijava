package com.otc.api.features.schedule.repository;

import com.otc.api.features.schedule.model.request.*;
import com.otc.api.features.schedule.model.response.*;
import com.otc.api.model.ActivityType;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {


    private final JdbcTemplate jdbcTemplate;

    private SimpleJdbcCall getScheduleListCall;
    private SimpleJdbcCall insertUpdateCall;
    private SimpleJdbcCall deleteCall;
    private SimpleJdbcCall routeCheckCall;
    private SimpleJdbcCall activityTypeCall;

    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void init() {
        this.getScheduleListCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("usp_GetScheduleList")
                .returningResultSet("schedules", (rs, i) -> mapScheduleItem(rs));

        this.insertUpdateCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("ScheduleRouteInsertV1");

        this.deleteCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_ATM_Schedule_Delete");

        this.routeCheckCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_ATM_Schedule_IsRouteConfig")
                .returningResultSet("result", (rs, i) -> rs.getString(1));

        this.activityTypeCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_GetIndentList")
                .returningResultSet("types", (rs, i) -> new ActivityType(rs.getString("SubVal")));
    }

    // used to fetch the schedule list
    @Override
    public List<ScheduleListItem> getScheduleList(ScheduleListRequest request) {
        Map<String, Object> params = Map.of(
                "FromDate", LocalDate.parse(request.fromDate()),
                "ToDate", LocalDate.parse(request.toDate()),
                "UserName", request.username()
        );

        Map<String, Object> result = getScheduleListCall.execute(params);

        @SuppressWarnings("unchecked")
        List<ScheduleListItem> list = (List<ScheduleListItem>) result.get("schedules");

        return list != null ? list : Collections.emptyList();
    }

    @Override
    public boolean insertSchedule(ScheduleInsertRequest request) {
        String scheduleId = generateScheduleId(request);

        Map<String, Object> params = new HashMap<>();
        params.put("Schedule_Id", scheduleId);
        params.put("ATMID", Optional.ofNullable(request.atmId()).orElse(""));
        params.put("Activity_Type", request.activityType());
        params.put("Schedule_Date", request.scheduleDate());
        params.put("CreatedDate", LocalDateTime.now());
        params.put("CreatedBy", request.username());
        params.put("LastModifiedDate", null);
        params.put("LastModifiedBy", null);
        params.put("Module", "Insert");
        params.put("Comment", "");

        insertUpdateCall.execute(params);
        return true;
    }

    @Override
    public boolean updateSchedule(ScheduleUpdateRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("Schedule_Id", request.scheduleId());
        params.put("ATMID", request.atmId());
        params.put("Activity_Type", request.activityType());
        params.put("Schedule_Date", request.scheduleDate());
        params.put("CreatedDate", null);
        params.put("CreatedBy", null);
        params.put("LastModifiedDate", LocalDateTime.now());
        params.put("LastModifiedBy", request.username());
        params.put("Module", "Update");
        params.put("Comment", request.comment());

        insertUpdateCall.execute(params);
        return true;
    }

    @Override
    public boolean deleteSchedule(ScheduleDeleteRequest request) {
        Map<String, Object> checkResult = routeCheckCall.execute(
                Map.of("ScheduleId", request.id())
        );

        List<?> result = (List<?>) checkResult.get("result");
        String status = (result != null && !result.isEmpty()) ? (String) result.get(0) : "";

        if ("Yes".equalsIgnoreCase(status)) {
            return false;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("ScheduleId", request.id());
        params.put("DeletedDate", LocalDateTime.now());
        params.put("DeletedBy", request.username());

        deleteCall.execute(params);
        return true;
    }

    @Override
    public List<ActivityType> getActivityTypes() {
        Map<String, Object> result = activityTypeCall.execute();

        @SuppressWarnings("unchecked")
        List<ActivityType> list = (List<ActivityType>) result.get("types");

        return list != null ? list : Collections.emptyList();
    }

    private String generateScheduleId(ScheduleInsertRequest request) {
        if (request.bulkScheduleInfo() != null && !request.bulkScheduleInfo().isEmpty()) {
            return "";
        }

        return jdbcTemplate.queryForObject(
                "select 'S' + RIGHT(REPLICATE('0',9) + CAST(NEXT VALUE FOR ScheduleSeq AS varchar),9)",
                String.class
        );
    }

    private ScheduleListItem mapScheduleItem(ResultSet rs) {
        try {
            return new ScheduleListItem(
                rs.getString("Schedule_Id"),
                rs.getString("ATMID"),
                rs.getString("Activity_Type"),
                rs.getString("Schedule_Date"),
                "", // createdDate mapping doesn't seem to exist in original model directly mapped from result set
                "", // createdBy
                "", // comment
                rs.getString("Status"),
                rs.getString("Region"),
                rs.getString("Location")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
