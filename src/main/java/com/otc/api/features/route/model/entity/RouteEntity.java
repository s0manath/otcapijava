package com.otc.api.features.route.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "RouteConfig")
public class RouteEntity {
    
    @Id
    private String routeConfigId;
    
    public String getRouteConfigId() {
        return routeConfigId;
    }

    public void setRouteConfigId(String routeConfigId) {
        this.routeConfigId = routeConfigId;
    }
}
