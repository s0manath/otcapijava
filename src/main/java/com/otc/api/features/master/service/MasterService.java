package com.otc.api.features.master.service;

import com.otc.api.features.master.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MasterService {

    private static final List<CustodianMaster> custodians = new ArrayList<>();
    private static final List<FranchiseMaster> franchises = new ArrayList<>();
    private static final List<AtmMaster> atms = new ArrayList<>();

    public List<CustodianMaster> getCustodians(String field, String startsWith, String chkLocked, String username) {
        return new ArrayList<>(custodians);
    }

    public CustodianMaster getCustodianById(int id) {
        return custodians.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public String saveCustodian(CustodianMaster custodian, String username) {
        if (custodian.getId() > 0) {
            custodians.removeIf(c -> c.getId() == custodian.getId());
        } else {
            custodian.setId(custodians.size() + 1);
        }
        custodians.add(custodian);
        return null; // Return error string if failed, null if success
    }

    public List<FranchiseMaster> getFranchises(String filterField, String filterValue) {
        return new ArrayList<>(franchises);
    }

    public FranchiseMaster getFranchiseById(int id) {
        return franchises.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
    }

    public String saveFranchise(FranchiseMaster franchise, String username) {
        if (franchise.getId() > 0) {
            franchises.removeIf(f -> f.getId() == franchise.getId());
        } else {
            franchise.setId(franchises.size() + 1);
        }
        franchises.add(franchise);
        return null;
    }

    public List<AtmMaster> getAtms() {
        return new ArrayList<>(atms);
    }

    public AtmMaster getAtmById(String atmId) {
        return atms.stream().filter(a -> a.getAtmId().equals(atmId)).findFirst().orElse(null);
    }

    public String saveAtm(AtmMaster atm, String username) {
        atms.removeIf(a -> a.getAtmId().equals(atm.getAtmId()));
        atms.add(atm);
        return null;
    }

    // Dropdowns
    public List<MasterDropdownItem> getLocations() {
        return Arrays.asList(new MasterDropdownItem("1", "Location 1"));
    }

    public List<MasterDropdownItem> getZoms() {
        return Arrays.asList(new MasterDropdownItem("1", "ZOM 1"));
    }

    public List<MasterDropdownItem> getFranchiseDropdown() {
        return Arrays.asList(new MasterDropdownItem("1", "Franchise 1"));
    }

    public List<MasterDropdownItem> getRouteKeys() {
        return Arrays.asList(new MasterDropdownItem("RK1", "Route Key 1"));
    }

    public List<MasterDropdownItem> getStates() {
        return Arrays.asList(new MasterDropdownItem("1", "State 1"));
    }

    public List<MasterDropdownItem> getDistrictsByState(int stateId) {
        return Arrays.asList(new MasterDropdownItem("1", "District 1"));
    }
}
