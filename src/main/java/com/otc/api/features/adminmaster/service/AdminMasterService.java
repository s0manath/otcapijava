package com.otc.api.features.adminmaster.service;

import com.otc.api.features.adminmaster.model.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminMasterService {

    private static final List<LocationMaster> locations = new ArrayList<>(Arrays.asList(
            new LocationMaster(1, "Downtown", "REG001", "North Region", true),
            new LocationMaster(2, "Uptown", "REG002", "South Region", true)
    ));

    private static final List<RegionMaster> regions = new ArrayList<>(Arrays.asList(
            new RegionMaster(1, "REG001", "North Region", true),
            new RegionMaster(2, "REG002", "South Region", true)
    ));

    private static final List<PendingLoginRequest> pendingRequests = new ArrayList<>(Arrays.asList(
            new PendingLoginRequest(1, "cust_user1", "John Doe", LocalDateTime.now().minusDays(1), "Login", "Android 13, Samsung S22", "New device registration", "Pending"),
            new PendingLoginRequest(2, "zom_user1", "Jane Smith", LocalDateTime.now().minusHours(5), "Password Reset", "iOS 16, iPhone 14", "Forgot password", "Pending")
    ));

    private static final List<KeyInventoryMaster> keys = new ArrayList<>();
    private static final List<OneLineMaster> oneLines = new ArrayList<>();
    private static final List<SiteAccessMaster> siteAccess = new ArrayList<>();
    private static final List<RouteMasterAdmin> routeMasters = new ArrayList<>();
    private static final List<CustodianLoginMapping> custodianMappings = new ArrayList<>();
    private static final List<ZomLoginMapping> zomMappings = new ArrayList<>();

    public List<LocationMaster> getLocations(AdminMasterSearchRequest request) {
        return locations.stream()
                .filter(l -> request.getQuery() == null || l.getLocationName().toLowerCase().contains(request.getQuery().toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean saveLocation(LocationMaster location) {
        if (location.getId() > 0) {
            locations.stream()
                    .filter(l -> l.getId() == location.getId())
                    .findFirst()
                    .ifPresent(existing -> {
                        existing.setLocationName(location.getLocationName());
                        existing.setRegionCode(location.getRegionCode());
                        existing.setRegionName(location.getRegionName());
                        existing.setIsActive(location.isIsActive());
                    });
        } else {
            int nextId = locations.isEmpty() ? 1 : locations.stream().mapToInt(LocationMaster::getId).max().getAsInt() + 1;
            location.setId(nextId);
            locations.add(location);
        }
        return true;
    }

    public boolean deleteLocation(int id) {
        return locations.removeIf(l -> l.getId() == id);
    }

    public List<RegionMaster> getRegions(AdminMasterSearchRequest request) {
        return new ArrayList<>(regions);
    }

    public boolean saveRegion(RegionMaster region) {
        if (region.getId() > 0) {
            regions.stream()
                    .filter(r -> r.getId() == region.getId())
                    .findFirst()
                    .ifPresent(existing -> {
                        existing.setRegionCode(region.getRegionCode());
                        existing.setRegionName(region.getRegionName());
                        existing.setIsActive(region.isIsActive());
                    });
        } else {
            int nextId = regions.isEmpty() ? 1 : regions.stream().mapToInt(RegionMaster::getId).max().getAsInt() + 1;
            region.setId(nextId);
            regions.add(region);
        }
        return true;
    }

    public boolean deleteRegion(int id) {
        return regions.removeIf(r -> r.getId() == id);
    }

    public List<KeyInventoryMaster> getKeyInventory(AdminMasterSearchRequest request) { return new ArrayList<>(keys); }
    public boolean saveKeyInventory(KeyInventoryMaster key) {
        if (key.getId() <= 0) key.setId(keys.size() + 1);
        keys.add(key);
        return true;
    }

    public List<OneLineMaster> getOneLineMasters(AdminMasterSearchRequest request) { return new ArrayList<>(oneLines); }
    public boolean saveOneLineMaster(OneLineMaster master) { return true; }

    public List<SiteAccessMaster> getSiteAccessMasters(AdminMasterSearchRequest request) { return new ArrayList<>(siteAccess); }
    public boolean saveSiteAccessMaster(SiteAccessMaster master) { return true; }

    public List<RouteMasterAdmin> getRouteMastersAdmin(AdminMasterSearchRequest request) { return new ArrayList<>(routeMasters); }
    public boolean saveRouteMasterAdmin(RouteMasterAdmin route) { return true; }

    public List<CustodianLoginMapping> getCustodianLoginMappings() { return new ArrayList<>(custodianMappings); }
    public boolean saveCustodianLoginMapping(CustodianLoginMapping mapping) {
        custodianMappings.add(mapping);
        return true;
    }

    public List<ZomLoginMapping> getZomLoginMappings() { return new ArrayList<>(zomMappings); }
    public boolean saveZomLoginMapping(ZomLoginMapping mapping) {
        zomMappings.add(mapping);
        return true;
    }

    public List<PendingLoginRequest> getPendingLoginRequests() { return new ArrayList<>(pendingRequests); }
    public boolean processLoginRequest(MappingApprovalRequest request) {
        pendingRequests.stream()
                .filter(p -> p.getId() == request.getRequestId())
                .findFirst()
                .ifPresent(p -> {
                    p.setStatus(request.isIsApproved() ? "Approved" : "Rejected");
                    p.setComments(request.getComments());
                });
        return true;
    }

    public boolean bulkUpdateRouteKeys(List<String> atmIds, String routeKey) {
        return true;
    }
}
