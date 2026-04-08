package com.otc.api.features.dashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DistrictReportItem(
    @JsonProperty("District Name") String districtName,
    @JsonProperty("Total") int total,
    @JsonProperty("Completed") int completed,
    @JsonProperty("Reset and Completed") int resetAndCompleted,
    @JsonProperty("Skipped") int pending,
    @JsonProperty("DistrictCode") String districtCode
) {}
