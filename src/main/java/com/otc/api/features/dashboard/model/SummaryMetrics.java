package com.otc.api.features.dashboard.model;

public record SummaryMetrics(
    int total,
    int completed,
    int reset,
    int pending
) {}
