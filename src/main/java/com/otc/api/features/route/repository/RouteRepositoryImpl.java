package com.otc.api.features.route.repository;

import com.otc.api.features.route.model.request.*;
import com.otc.api.features.route.model.response.*;
import com.otc.api.model.FilterItem;
import com.otc.api.features.master.model.CustodianListItem;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class RouteRepositoryImpl implements RouteRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcCall getRouteListCall;
    private SimpleJdbcCall routeConfigCall;
    private SimpleJdbcCall routeConfigBulkCall;

    public RouteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void init() {
        this.getRouteListCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("spFillRouteConfig")
                .returningResultSet("routes", (rs, rowNum) -> new RouteListItem(
                        safeString(rs, "Schedule_Id"),
                        safeString(rs, "RouteConfig_Id"),
                        safeString(rs, "ATMID"),
                        safeString(rs, "Activity_Type"),
                        safeString(rs, "Schedule_Date"),
                        safeString(rs, "Region"),
                        safeString(rs, "Location"),
                        safeString(rs, "Franchise"),
                        safeString(rs, "ZOM"),
                        safeString(rs, "Status"),
                        safeString(rs, "RouteKey"),
                        safeString(rs, "Custodian1"),
                        safeString(rs, "Custodian2"),
                        safeString(rs, "CompletedDate")));

        this.routeConfigCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_RouteConfig");

        this.routeConfigBulkCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_RouteConfigBulk");
    }

    @Override
    public List<RouteListItem> getRouteList(RouteListRequest request) {
        Map<String, Object> params = new HashMap<>();

        if (request.fromDate() != null && !request.fromDate().isEmpty()) {
            params.put("DateFrom", LocalDate.parse(request.fromDate(), DateTimeFormatter.ISO_LOCAL_DATE));
        } else {
            params.put("DateFrom", null);
        }
        if (request.toDate() != null && !request.toDate().isEmpty()) {
            params.put("DateTo", LocalDate.parse(request.toDate(), DateTimeFormatter.ISO_LOCAL_DATE));
        } else {
            params.put("DateTo", null);
        }

        params.put("Region", request.region());
        params.put("Location", request.district());
        params.put("Franchise", request.franchise());
        params.put("ZOM", request.zom());
        params.put("ActivityType", request.activityType());
        params.put("FilterStatus", request.status());
        params.put("ChkConfig", request.chkConfig());
        params.put("Field", request.searchField());
        params.put("Criteria", request.criteria());
        params.put("Value", request.searchValue());
        params.put("Username", request.username() != null ? request.username() : "admin");

        Map<String, Object> result = getRouteListCall.execute(params);
        @SuppressWarnings("unchecked")
        List<RouteListItem> routes = (List<RouteListItem>) result.get("routes");
        return routes != null ? routes : new ArrayList<>();
    }

    @Override
    public RouteFilterOptions getFilterOptions() {
        List<FilterItem> regions = jdbcTemplate.query(
                "select roname as name, rocode as code from romast order by ROName asc",
                (rs, i) -> new FilterItem(rs.getString("name"), rs.getString("code")));
        List<FilterItem> districts = jdbcTemplate.query(
                "select district_name as name, district_id as code from DistrictMaster order by district_name asc",
                (rs, i) -> new FilterItem(rs.getString("name"), rs.getString("code")));
        List<FilterItem> franchises = jdbcTemplate.query(
                "select FranchiseName as name, FranchiseCode as code from FranchiseMaster order by FranchiseName asc",
                (rs, i) -> new FilterItem(rs.getString("name"), rs.getString("code")));
        List<FilterItem> zoms = jdbcTemplate.query(
                "select ZOMName as name, ZOMCode as code from ZOMMaster order by ZOMName asc",
                (rs, i) -> new FilterItem(rs.getString("name"), rs.getString("code")));
        List<String> activityTypes = jdbcTemplate.query(
                "select SubVal from submaster where [Key]='Indent' order by SubVal asc",
                (rs, i) -> rs.getString("SubVal"));

        return new RouteFilterOptions(regions, districts, franchises, zoms, activityTypes);
    }

    @Override
    public List<CustodianListItem> getCustodians(String scheduleId) {
        String sql = """
                select CustodianMaster.CustodianName, CustodianMaster.CustodianCode
                from ATM_Schedule
                inner join CustodianMapping on CustodianMapping.EquipId = ATM_Schedule.ATMID
                inner join CustodianMaster on CustodianMaster.CustodianCode = CustodianMapping.CustodianCode
                INNER JOIN RouteMaster ON RouteMaster.CustodianID = CustodianMaster.CustodianID AND RouteMaster.TouchKeyID = CustodianMaster.TouchKeyID
                where (lockflg is null or lockflg='' or lockflg=0) and Schedule_Id = ?
                """;
        return jdbcTemplate.query(sql,
                (rs, i) -> new CustodianListItem(rs.getString("CustodianName"), rs.getString("CustodianCode")),
                scheduleId);
    }

    @Override
    public boolean saveRoute(RouteSaveRequest request) {
        String checkSql = "select RouteConfig_Id, RouteKey from RouteConfig WITH (NOLOCK) where Schedule_Id = ?";
        List<Map<String, Object>> existing = jdbcTemplate.queryForList(checkSql, request.scheduleId());

        String module = existing.isEmpty() ? "Insert" : "Update";
        boolean isBulk = request.updateAll() && !existing.isEmpty() &&
                request.routeKey().equals(existing.get(0).get("RouteKey"));

        SimpleJdbcCall call = isBulk ? routeConfigBulkCall : routeConfigCall;

        Map<String, Object> params = new HashMap<>();
        params.put("AtmId", request.atmId());
        params.put("RouteConfig_Id", existing.isEmpty() ? "" : existing.get(0).get("RouteConfig_Id"));
        params.put("Schedule_Id", request.scheduleId());
        params.put("RouteKey", request.routeKey());
        params.put("Custodian1", request.custodian1());
        params.put("Custodian2", request.custodian2());
        params.put("CreatedBy", request.username());
        params.put("Module", module);

        call.execute(params);
        return true;
    }

    private String safeString(java.sql.ResultSet rs, String col) {
        try {
            return rs.getString(col);
        } catch (Exception e) {
            return "";
        }
    }
}
