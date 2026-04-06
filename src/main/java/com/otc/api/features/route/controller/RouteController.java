package com.otc.api.features.route.controller;

import com.otc.api.features.route.model.request.*;
import com.otc.api.features.route.model.response.*;
import com.otc.api.features.route.service.RouteService;
import com.otc.api.features.master.model.CustodianListItem;
import com.otc.api.model.StringIdRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Route")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<RouteListItem>> getList(@RequestBody RouteListRequest request) {
        return ResponseEntity.ok(routeService.getRouteList(request));
    }

    @PostMapping("/filters")
    public ResponseEntity<RouteFilterOptions> getFilters() {
        return ResponseEntity.ok(routeService.getFilterOptions());
    }

    @PostMapping("/custodians")
    public ResponseEntity<List<CustodianListItem>> getCustodians(@RequestBody StringIdRequest request) {
        return ResponseEntity.ok(routeService.getCustodians(request.id()));
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody RouteSaveRequest request) {
        boolean success = routeService.saveRoute(request);
        if (success) {
            return ResponseEntity.ok(new MessageResponse("Route configured successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Failed to configure route"));
    }

    public record MessageResponse(String message) {
    }
}
