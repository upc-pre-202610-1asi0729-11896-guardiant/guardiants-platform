package com.guardiants.platform.commands.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.commands.infrastructure.persistence.jpa.entities.TheftReportPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheftReportPersistenceRepository
        extends JpaRepository<TheftReportPersistenceEntity, Long> {
    List<TheftReportPersistenceEntity> findAllByStatus(String status);
}
