package com.otc.api.features.schedule.repository;

import com.otc.api.features.schedule.model.request.*;
import com.otc.api.features.schedule.model.response.*;
import com.otc.api.model.ActivityType;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Repository
public class ScheduleRepositoryImpl implements ScheduleRepositoryCustom {


    private static final Logger log = LoggerFactory.getLogger(ScheduleRepositoryImpl.class);
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
                .withProcedureName("India1_usp_GetScheduleList")
                .returningResultSet("schedules", (rs, i) -> mapScheduleItem(rs));

        this.insertUpdateCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("India1_USP_ScheduleRouteInsertV1");

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
                "FromDate", LocalDate.parse(request.getFromDate()),
                "ToDate", LocalDate.parse(request.getToDate()),
                "UserName", request.getUsername()
        );

        Map<String, Object> result = getScheduleListCall.execute(params);

        @SuppressWarnings("unchecked")
        List<ScheduleListItem> list = (List<ScheduleListItem>) result.get("schedules");

        return list != null ? list : Collections.emptyList();
    }

    @Override
    public boolean insertSchedule(ScheduleInsertRequest request) {

        try{
            String scheduleId = generateScheduleId(request);

        Map<String, Object> params = new HashMap<>();
        params.put("Schedule_Id", scheduleId);
        params.put("ATMID", Optional.ofNullable(request.getAtmId()).orElse(""));
        params.put("Activity_Type", request.getActivityType());
        params.put("Schedule_Date", request.getScheduleDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        params.put("CreatedDate", LocalDateTime.now().format(formatter));
        params.put("CreatedBy", request.getUsername());
        params.put("LastModifiedDate", null);
        params.put("LastModifiedBy", null);
        params.put("Module", "Insert");
        params.put("Comment",request.getComment());

            insertUpdateCall.execute(params);
            return true;
        }
        catch (Exception ex){
            log.error(ex.getMessage());
            return false;
        }


    }    @Override
    public boolean updateSchedule(ScheduleUpdateRequest request) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("Schedule_Id", request.scheduleId());
            params.put("ATMID", Optional.ofNullable(request.atmId()).orElse(""));
            params.put("Activity_Type", request.activityType());
            params.put("Schedule_Date", request.scheduleDate());
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            params.put("CreatedDate", "");
            params.put("CreatedBy", "");
            params.put("LastModifiedDate", LocalDateTime.now().format(formatter));
            params.put("LastModifiedBy", request.username());
            params.put("Module", "Update");
            params.put("Comment", request.comment());

            insertUpdateCall.execute(params);
            return true;
        } catch (Exception ex) {
            log.error("Update schedule failed: {}", ex.getMessage());
            return false;
        }
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
        if (request.getBulkScheduleInfo() != null && !request.getBulkScheduleInfo().isEmpty()) {
            return "";
        }

        return jdbcTemplate.queryForObject(
                "SELECT 'S' + RIGHT(Replicate(0, 9) + Cast(schedule_id AS VARCHAR(7)), 9) AS'ID' FROM   keygeneration WITH (nolock)",
                String.class
        );
    }

    private ScheduleListItem mapScheduleItem(ResultSet rs) {
        try {
            return new ScheduleListItem(
                rs.getString("ScheduleId"),
                rs.getString("AtmId"),
                rs.getString("ActivityType"),
                rs.getString("ScheduleDate"),
                rs.getString("CreatedDate"),
                rs.getString("CreatedBy"),
                rs.getString("Comment")
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
