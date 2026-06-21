package com.guardiants.platform.alerting.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.alerting.infrastructure.persistence.jpa.entities.AlertRulePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRulePersistenceRepository
        extends JpaRepository<AlertRulePersistenceEntity, Long> {
}
