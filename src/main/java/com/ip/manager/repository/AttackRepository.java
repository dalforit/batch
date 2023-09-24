package com.ip.manager.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ip.manager.entity.AttackEntity;
import com.ip.manager.utils.enums.SeverityEnum;

@Repository
public interface AttackRepository extends JpaRepository<AttackEntity, Long> {

    List<AttackEntity> findBySeverity(SeverityEnum severity);

    List<AttackEntity> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

}
