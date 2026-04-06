package com.otc.api.features.route.repository;

import com.otc.api.features.route.model.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<RouteEntity, String>, RouteRepositoryCustom {
}
