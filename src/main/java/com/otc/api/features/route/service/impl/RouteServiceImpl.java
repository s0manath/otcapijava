package com.otc.api.features.route.service.impl;

import com.otc.api.features.route.model.request.*;
import com.otc.api.features.route.model.response.*;
import com.otc.api.features.route.repository.RouteRepository;
import com.otc.api.features.master.model.CustodianListItem;
import org.springframework.stereotype.Service;

import java.util.List;

import com.otc.api.features.route.service.RouteService;

@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<RouteListItem> getRouteList(RouteListRequest request) {
        return routeRepository.getRouteList(request);
    }

    public RouteFilterOptions getFilterOptions() {
        return routeRepository.getFilterOptions();
    }

    public List<CustodianListItem> getCustodians(String scheduleId) {
        return routeRepository.getCustodians(scheduleId);
    }

    public RouteListItem getRouteDetails(String scheduleId) {
        return routeRepository.getRouteDetails(scheduleId);
    }

    public boolean saveRoute(RouteSaveRequest request) {
        return routeRepository.saveRoute(request);
    }
}
