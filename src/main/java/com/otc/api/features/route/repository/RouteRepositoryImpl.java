package com.otc.api.features.route.repository;

import com.otc.api.features.route.model.request.*;
import com.otc.api.features.route.model.response.*;
import com.otc.api.model.FilterItem;
import com.otc.api.features.master.model.CustodianListItem;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDate;
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

    /**
     * Initializes stored procedure calls.
     */
    @PostConstruct
    private void init() {

        this.getRouteListCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("India1_USP_FillRouteConfig")
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("DateFrom", Types.TIMESTAMP),
                        new SqlParameter("DateTo", Types.TIMESTAMP),
                        new SqlParameter("UserName", Types.VARCHAR)
                )
                .returningResultSet("routes", (rs, i) -> mapRouteItem(rs));

        this.routeConfigCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_RouteConfig");

        this.routeConfigBulkCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_RouteConfigBulk");
    }

    /**
     * Fetches route list based on request filters. Completed
     */
    @Override
    public List<RouteListItem> getRouteList(RouteListRequest request) {
        try {
            Map<String, Object> params = new HashMap<>();

            params.put("UserName", request.username() != null ? request.username() : "Likhith");

            params.put("DateFrom",
                    request.fromDate() != null && !request.fromDate().isEmpty()
                            ? Timestamp.valueOf(LocalDate.parse(request.fromDate()).atStartOfDay())
                            : null);

            params.put("DateTo",
                    request.toDate() != null && !request.toDate().isEmpty()
                            ? Timestamp.valueOf(LocalDate.parse(request.toDate()).atTime(23, 59, 59))
                            : null);

            Map<String, Object> result = getRouteListCall.execute(params);

            List<RouteListItem> routes = (List<RouteListItem>) result.get("routes");

            return routes != null ? routes : new ArrayList<>();

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch route list", e);
        }
    }

    /**
     * Retrieves filter options such as regions, districts, franchises, ZOMs, and activity types.
     */
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

    /**
     * Retrieves custodian list for a given schedule ID.
     */
    @Override
    public List<CustodianListItem> getCustodians(String scheduleId) {

        String sql = """
                select CustodianMaster.CustodianName, CustodianMaster.CustodianCode
                from ATM_Schedule
                inner join CustodianMapping on CustodianMapping.EquipId = ATM_Schedule.ATMID
                inner join CustodianMaster on CustodianMaster.CustodianCode = CustodianMapping.CustodianCode
                INNER JOIN RouteMaster ON RouteMaster.CustodianID = CustodianMaster.CustodianID 
                    AND RouteMaster.TouchKeyID = CustodianMaster.TouchKeyID
                where (lockflg is null or lockflg='' or lockflg=0) 
                and Schedule_Id = ?
                """;

        return jdbcTemplate.query(sql,
                (rs, i) -> new CustodianListItem(
                        rs.getString("CustodianName"),
                        rs.getString("CustodianCode")
                ),
                scheduleId);
    }

    /**
     * Saves or updates route configuration.
     */
    @Override
    public boolean saveRoute(RouteSaveRequest request) {

        String checkSql = "select RouteConfig_Id, RouteKey from RouteConfig WITH (NOLOCK) where Schedule_Id = ?";
        List<Map<String, Object>> existing = jdbcTemplate.queryForList(checkSql, request.scheduleId());

        String module = existing.isEmpty() ? "Insert" : "Update";

        boolean isBulk = request.updateAll()
                && !existing.isEmpty()
                && request.routeKey().equals(existing.get(0).get("RouteKey"));

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

    /**
     * Maps ResultSet row to RouteListItem.
     */
    private RouteListItem mapRouteItem(ResultSet rs) {
        try {
            return new RouteListItem(
                    safeString(rs, "ScheduleId"),
                    safeString(rs, "RouteId"),
                    safeString(rs, "AtmId"),
                    safeString(rs, "ActivityType"),
                    safeString(rs, "ScheduleDate"),
                    safeString(rs, "Region"),
                    safeString(rs, "FranchiseName"),
                    safeString(rs, "ZomName"),
                    safeString(rs, "Status"),
                    safeString(rs, "Routekey"),
                    safeString(rs, "Custodian1"),
                    safeString(rs, "Custodian2"),
                    safeString(rs, "DistrictName"),
                    safeString(rs, "CroType")
            );
        } catch (Exception e) {
            throw new RuntimeException("Error mapping RouteListItem", e);
        }
    }

    /**
     * Safely retrieves string value from ResultSet.
     */
    private String safeString(ResultSet rs, String col) {
        try {
            String val = rs.getString(col);
            return val != null ? val : "";
        } catch (Exception e) {
            return "";
        }
    }
}