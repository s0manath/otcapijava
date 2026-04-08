package com.otc.api.features.dashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ChartData(
    @JsonProperty("Name") String name,
    @JsonProperty("Value") double value
) {}
