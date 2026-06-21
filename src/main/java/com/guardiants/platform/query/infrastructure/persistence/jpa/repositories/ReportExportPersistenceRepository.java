package com.guardiants.platform.query.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.ReportExportPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportExportPersistenceRepository
        extends JpaRepository<ReportExportPersistenceEntity, Long> {
}
