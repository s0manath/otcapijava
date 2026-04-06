package com.otc.api.features.schedule.repository;

import com.otc.api.features.schedule.model.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, String>, ScheduleRepositoryCustom {
}
