package com.otc.api.features.adminmaster.controller;

import com.otc.api.features.adminmaster.model.*;
import com.otc.api.features.adminmaster.service.AdminMasterService;
import com.otc.api.model.IdRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/AdminMaster")
public class AdminMasterController {

    private final AdminMasterService adminService;

    public AdminMasterController(AdminMasterService adminService) {
        this.adminService = adminService;
    }

    // Masters

    @PostMapping("/locations-list")
    public ResponseEntity<List<LocationMaster>> getLocations(@RequestBody AdminMasterSearchRequest request) {
        return ResponseEntity.ok(adminService.getLocations(request));
    }

    @PostMapping("/locations")
    public ResponseEntity<Boolean> saveLocation(@RequestBody LocationMaster location) {
        return ResponseEntity.ok(adminService.saveLocation(location));
    }

    @PostMapping("/locations-delete")
    public ResponseEntity<Boolean> deleteLocation(@RequestBody IdRequest request) {
        return ResponseEntity.ok(adminService.deleteLocation(request.getId()));
    }

    @PostMapping("/regions-list")
    public ResponseEntity<List<RegionMaster>> getRegions(@RequestBody AdminMasterSearchRequest request) {
        return ResponseEntity.ok(adminService.getRegions(request));
    }

    @PostMapping("/regions")
    public ResponseEntity<Boolean> saveRegion(@RequestBody RegionMaster region) {
        return ResponseEntity.ok(adminService.saveRegion(region));
    }

    @PostMapping("/key-inventory-list")
    public ResponseEntity<List<KeyInventoryMaster>> getKeyInventory(@RequestBody AdminMasterSearchRequest request) {
        return ResponseEntity.ok(adminService.getKeyInventory(request));
    }

    @PostMapping("/key-inventory")
    public ResponseEntity<Boolean> saveKeyInventory(@RequestBody KeyInventoryMaster key) {
        return ResponseEntity.ok(adminService.saveKeyInventory(key));
    }

    @PostMapping("/one-lines-list")
    public ResponseEntity<List<OneLineMaster>> getOneLines(@RequestBody AdminMasterSearchRequest request) {
        return ResponseEntity.ok(adminService.getOneLineMasters(request));
    }

    @PostMapping("/one-lines")
    public ResponseEntity<Boolean> saveOneLine(@RequestBody OneLineMaster master) {
        return ResponseEntity.ok(adminService.saveOneLineMaster(master));
    }

    @PostMapping("/site-access-list")
    public ResponseEntity<List<SiteAccessMaster>> getSiteAccess(@RequestBody AdminMasterSearchRequest request) {
        return ResponseEntity.ok(adminService.getSiteAccessMasters(request));
    }

    @PostMapping("/site-access")
    public ResponseEntity<Boolean> saveSiteAccess(@RequestBody SiteAccessMaster master) {
        return ResponseEntity.ok(adminService.saveSiteAccessMaster(master));
    }

    @PostMapping("/route-masters-list")
    public ResponseEntity<List<RouteMasterAdmin>> getRouteMasters(@RequestBody AdminMasterSearchRequest request) {
        return ResponseEntity.ok(adminService.getRouteMastersAdmin(request));
    }

    @PostMapping("/route-masters")
    public ResponseEntity<Boolean> saveRouteMaster(@RequestBody RouteMasterAdmin route) {
        return ResponseEntity.ok(adminService.saveRouteMasterAdmin(route));
    }

    // Mappings & Requests

    @PostMapping("/mappings/custodian-list")
    public ResponseEntity<List<CustodianLoginMapping>> getCustodianMappings() {
        return ResponseEntity.ok(adminService.getCustodianLoginMappings());
    }

    @PostMapping("/mappings/custodian")
    public ResponseEntity<Boolean> saveCustodianMapping(@RequestBody CustodianLoginMapping mapping) {
        return ResponseEntity.ok(adminService.saveCustodianLoginMapping(mapping));
    }

    @PostMapping("/mappings/zom-list")
    public ResponseEntity<List<ZomLoginMapping>> getZomMappings() {
        return ResponseEntity.ok(adminService.getZomLoginMappings());
    }

    @PostMapping("/mappings/zom")
    public ResponseEntity<Boolean> saveZomMapping(@RequestBody ZomLoginMapping mapping) {
        return ResponseEntity.ok(adminService.saveZomLoginMapping(mapping));
    }

    @PostMapping("/pending-requests-list")
    public ResponseEntity<List<PendingLoginRequest>> getPendingRequests() {
        return ResponseEntity.ok(adminService.getPendingLoginRequests());
    }

    @PostMapping("/process-request")
    public ResponseEntity<Boolean> processRequest(@RequestBody MappingApprovalRequest request) {
        return ResponseEntity.ok(adminService.processLoginRequest(request));
    }

    // Utilities

    @PostMapping("/bulk-update-route-keys")
    public ResponseEntity<Boolean> bulkUpdateRouteKeys(@RequestBody BulkUpdateRouteKeyRequest request) {
        // Since BulkUpdateRouteKeyRequest is package-private in CommonModels, this works if in same package.
        // But Controller is in .controller, so it needs to be public.
        // I'll update CommonModels to make it public if needed.
        return ResponseEntity.ok(adminService.bulkUpdateRouteKeys(request.getAtmIds(), request.getRouteKey()));
    }
}
