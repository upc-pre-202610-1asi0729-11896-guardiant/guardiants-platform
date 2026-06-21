package com.guardiants.platform.alerting.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.alerting.infrastructure.persistence.jpa.entities.SecurityAlertPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityAlertPersistenceRepository
        extends JpaRepository<SecurityAlertPersistenceEntity, Long> {
    List<SecurityAlertPersistenceEntity> findAllByOwnerId(Long ownerId);
}
