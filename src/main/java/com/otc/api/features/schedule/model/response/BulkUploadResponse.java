package com.otc.api.features.schedule.model.response;

import java.util.List;

public record BulkUploadResponse(
    boolean success,
    String message,
    int totalRecords,
    int validRecords,
    int invalidRecords,
    List<BulkError> errors,
    String resultFileBase64 // Returning the result excel as base64 for simplicity in this flow
) {
    public record BulkError(
        int rowNumber,
        String columnName,
        String errorMessage
    ) {}
}
