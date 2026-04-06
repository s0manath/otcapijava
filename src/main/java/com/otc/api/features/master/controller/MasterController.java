package com.otc.api.features.master.controller;

import com.otc.api.features.master.model.*;
import com.otc.api.features.master.service.MasterService;
import com.otc.api.model.IdRequest;
import com.otc.api.model.StringIdRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Master")
public class MasterController {

    private final MasterService masterService;

    public MasterController(MasterService masterService) {
        this.masterService = masterService;
    }

    // Custodian Master

    @PostMapping("/custodians-list")
    public ResponseEntity<List<CustodianMaster>> getCustodians(@RequestBody CustodianSearchRequest request) {
        String userName = "Admin"; // In a real app, get from Spring Security Context
        List<CustodianMaster> result = masterService.getCustodians(request.getField(), request.getStartsWith(), request.getChkLocked(), userName);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/custodians-detail")
    public ResponseEntity<CustodianMaster> getCustodian(@RequestBody IdRequest request) {
        CustodianMaster result = masterService.getCustodianById(request.getId());
        if (result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/custodians")
    public ResponseEntity<?> saveCustodian(@RequestBody CustodianMaster custodian) {
        String userName = "Admin";
        String error = masterService.saveCustodian(custodian, userName);
        if (error != null && !error.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse(error));
        }
        return ResponseEntity.ok(new MessageResponse("Saved successfully"));
    }

    // Franchise Master

    @PostMapping("/franchises-list")
    public ResponseEntity<List<FranchiseMaster>> getFranchises(@RequestBody FranchiseSearchRequest request) {
        List<FranchiseMaster> result = masterService.getFranchises(request.getFilterField(), request.getFilterValue());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/franchises-detail")
    public ResponseEntity<FranchiseMaster> getFranchise(@RequestBody IdRequest request) {
        FranchiseMaster result = masterService.getFranchiseById(request.getId());
        if (result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/franchises")
    public ResponseEntity<?> saveFranchise(@RequestBody FranchiseMaster franchise) {
        String userName = "Admin";
        String error = masterService.saveFranchise(franchise, userName);
        if (error != null && !error.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse(error));
        }
        return ResponseEntity.ok(new MessageResponse("Saved successfully"));
    }

    // ATM Master

    @PostMapping("/atms-list")
    public ResponseEntity<List<AtmMaster>> getAtms() {
        List<AtmMaster> result = masterService.getAtms();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/atms-detail")
    public ResponseEntity<AtmMaster> getAtm(@RequestBody StringIdRequest request) {
        AtmMaster result = masterService.getAtmById(request.id());
        if (result == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/atms")
    public ResponseEntity<?> saveAtm(@RequestBody AtmMaster atm) {
        String userName = "Admin";
        String error = masterService.saveAtm(atm, userName);
        if (error != null && !error.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse(error));
        }
        return ResponseEntity.ok(new MessageResponse("Saved successfully"));
    }

    // Dropdowns

    @PostMapping("/dropdowns/locations")
    public ResponseEntity<List<MasterDropdownItem>> getLocations() {
        return ResponseEntity.ok(masterService.getLocations());
    }

    @PostMapping("/dropdowns/zoms")
    public ResponseEntity<List<MasterDropdownItem>> getZoms() {
        return ResponseEntity.ok(masterService.getZoms());
    }

    @PostMapping("/dropdowns/franchises")
    public ResponseEntity<List<MasterDropdownItem>> getFranchiseDropdown() {
        return ResponseEntity.ok(masterService.getFranchiseDropdown());
    }

    @PostMapping("/dropdowns/routekeys")
    public ResponseEntity<List<MasterDropdownItem>> getRouteKeys() {
        return ResponseEntity.ok(masterService.getRouteKeys());
    }

    @PostMapping("/dropdowns/states")
    public ResponseEntity<List<MasterDropdownItem>> getStates() {
        return ResponseEntity.ok(masterService.getStates());
    }

    @PostMapping("/dropdowns/districts")
    public ResponseEntity<List<MasterDropdownItem>> getDistricts(@RequestBody IdRequest request) {
        return ResponseEntity.ok(masterService.getDistrictsByState(request.getId()));
    }

    // Helper class for message responses
    private static class MessageResponse {
        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
