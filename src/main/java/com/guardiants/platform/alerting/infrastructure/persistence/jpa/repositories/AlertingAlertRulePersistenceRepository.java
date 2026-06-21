package com.guardiants.platform.alerting.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.alerting.infrastructure.persistence.jpa.entities.AlertRulePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for alerting alert rules. Named distinctly from the Fleet BC's
 * {@code AlertRulePersistenceRepository} to avoid a duplicate Spring bean definition.
 */
@Repository
public interface AlertingAlertRulePersistenceRepository
        extends JpaRepository<AlertRulePersistenceEntity, Long> {
    List<AlertRulePersistenceEntity> findAllByOwnerId(Long ownerId);
}
