package com.guardiants.platform.alerting.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.alerting.infrastructure.persistence.jpa.entities.AlertRulePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRulePersistenceRepository
        extends JpaRepository<AlertRulePersistenceEntity, Long> {
    List<AlertRulePersistenceEntity> findAllByOwnerId(Long ownerId);
}
