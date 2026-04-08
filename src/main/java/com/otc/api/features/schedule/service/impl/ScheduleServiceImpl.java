package com.otc.api.features.schedule.service.impl;

import com.otc.api.features.schedule.model.request.*;
import com.otc.api.features.schedule.model.response.*;
import com.otc.api.features.schedule.repository.ScheduleRepository;
import com.otc.api.model.ActivityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import com.otc.api.features.schedule.service.ScheduleService;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<ScheduleListItem> getScheduleList(ScheduleListRequest request) {
        try {
            return scheduleRepository.getScheduleList(request);
        } catch (Exception e) {
            logger.error("getScheduleList failed", e);
            throw new RuntimeException("Unable to fetch schedules", e);
        }
    }

    public boolean insertSchedule(ScheduleInsertRequest request) {
        try {
            return scheduleRepository.insertSchedule(request);
        } catch (Exception e) {
            logger.error("insertSchedule failed", e);
            return false;
        }
    }

    public boolean updateSchedule(ScheduleUpdateRequest request) {
        try {
            return scheduleRepository.updateSchedule(request);
        } catch (Exception e) {
            logger.error("updateSchedule failed", e);
            return false;
        }
    }

    public boolean deleteSchedule(ScheduleDeleteRequest request) {
        try {
            return scheduleRepository.deleteSchedule(request);
        } catch (Exception e) {
            logger.error("deleteSchedule failed", e);
            return false;
        }
    }

    public List<ActivityType> getActivityTypes() {
        try {
            return scheduleRepository.getActivityTypes();
        } catch (Exception e) {
            logger.error("getActivityTypes failed", e);
            return Collections.emptyList();
        }
    }

    @Override
    public BulkUploadResponse processBulkUpload(MultipartFile file, String username) {
        List<BulkUploadResponse.BulkError> errors = new ArrayList<>();
        int totalRecords = 0;
        int successCount = 0;
        int errorCount = 0;

        List<ActivityType> validTypes = getActivityTypes();
        Set<String> validTypeNames = validTypes.stream()
                .map(ActivityType::name)
                .collect(Collectors.toSet());

        try (InputStream is = file.getInputStream();
             Workbook workbook = WorkbookFactory.create(is)) {

            Sheet sheet = workbook.getSheetAt(0);
            totalRecords = sheet.getLastRowNum();

            Row headerRow = sheet.getRow(0);
            int statusColIdx = headerRow.getLastCellNum();
            headerRow.createCell(statusColIdx).setCellValue("Status");
            headerRow.createCell(statusColIdx + 1).setCellValue("Message");

            SimpleDateFormat inputDateFormat = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            inputDateFormat.setLenient(false);

            for (int i = 1; i <= totalRecords; i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String atmId = getCellValue(row.getCell(0));
                String activityType = getCellValue(row.getCell(1));
                String scheduleDateStr = getCellValue(row.getCell(2));
                String comment = getCellValue(row.getCell(3));

                StringBuilder rowError = new StringBuilder();

                if (atmId == null || !atmId.matches("\\d{8}")) {
                    rowError.append("Invalid ATM ID (Must be 8 digits). ");
                }

                if (activityType == null || !validTypeNames.contains(activityType)) {
                    rowError.append("Invalid Activity Type. ");
                }

                String formattedDate = "";
                try {
                    if (scheduleDateStr == null || !scheduleDateStr.matches("\\d{8}")) {
                        throw new Exception("Invalid date format");
                    }
                    Date date = inputDateFormat.parse(scheduleDateStr);
                    formattedDate = outputDateFormat.format(date);
                } catch (Exception e) {
                    rowError.append("Invalid Date (Must be DDMMYYYY). ");
                }

                if (rowError.length() == 0) {
                    ScheduleInsertRequest request = new ScheduleInsertRequest();
                    request.setAtmId(atmId);
                    request.setActivityType(activityType);
                    request.setScheduleDate(formattedDate);
                    request.setComment(comment);
                    request.setUsername(username);

                    if (scheduleRepository.insertSchedule(request)) {
                        successCount++;
                        row.createCell(statusColIdx).setCellValue("Success");
                    } else {
                        errorCount++;
                        row.createCell(statusColIdx).setCellValue("Error");
                        row.createCell(statusColIdx + 1).setCellValue("Database insertion failed");
                        errors.add(new BulkUploadResponse.BulkError(i + 1, "Database", "Failed to insert record"));
                    }
                } else {
                    errorCount++;
                    row.createCell(statusColIdx).setCellValue("Error");
                    row.createCell(statusColIdx + 1).setCellValue(rowError.toString().trim());
                    errors.add(new BulkUploadResponse.BulkError(i + 1, "Validation", rowError.toString().trim()));
                }
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            String base64Result = Base64.getEncoder().encodeToString(bos.toByteArray());

            return new BulkUploadResponse(true, "Processing complete", totalRecords, successCount, errorCount, errors, base64Result);

        } catch (Exception e) {
            logger.error("Bulk upload processing failed", e);
            return new BulkUploadResponse(false, "Failed to process file: " + e.getMessage(), 0, 0, 0, java.util.Collections.emptyList(), null);
        }
    }

    @Override
    public byte[] generateTemplate() {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet("Schedule Template");
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ATM ID (8 Digits)");
            header.createCell(1).setCellValue("Activity Type");
            header.createCell(2).setCellValue("Schedule Date (DDMMYYYY)");
            header.createCell(3).setCellValue("Comment");

            Row sample = sheet.createRow(1);
            sample.createCell(0).setCellValue("12345678");
            sample.createCell(1).setCellValue("Cash Loading");
            sample.createCell(2).setCellValue("10042024");
            sample.createCell(3).setCellValue("Sample comment");

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);

            workbook.write(bos);
            return bos.toByteArray();
        } catch (Exception e) {
            logger.error("Template generation failed", e);
            return new byte[0];
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue().trim();
            case NUMERIC: 
                if (DateUtil.isCellDateFormatted(cell)) {
                    return new SimpleDateFormat("ddMMyyyy").format(cell.getDateCellValue());
                }
                return String.format("%.0f", cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            default: return "";
        }
    }
}