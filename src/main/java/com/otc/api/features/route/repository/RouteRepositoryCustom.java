package com.otc.api.features.route.repository;

import com.otc.api.features.route.model.request.*;
import com.otc.api.features.route.model.response.*;
import com.otc.api.features.master.model.CustodianListItem;
import java.util.List;

public interface RouteRepositoryCustom {
    List<RouteListItem> getRouteList(RouteListRequest request);

    RouteFilterOptions getFilterOptions();

    List<CustodianListItem> getCustodians(String scheduleId);

    boolean saveRoute(RouteSaveRequest request);

}
