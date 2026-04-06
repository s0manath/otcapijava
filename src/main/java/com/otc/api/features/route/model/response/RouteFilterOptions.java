package com.otc.api.features.route.model.response;

import com.otc.api.model.FilterItem;
import java.util.List;

public record RouteFilterOptions(
    List<FilterItem> regions,
    List<FilterItem> districts,
    List<FilterItem> franchises,
    List<FilterItem> zoms,
    List<String> activityTypes
) {}
