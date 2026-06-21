package com.guardiants.platform.commands.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.commands.infrastructure.persistence.jpa.entities.TheftReportCommandLinkPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheftReportCommandLinkPersistenceRepository
        extends JpaRepository<TheftReportCommandLinkPersistenceEntity, Long> {
    List<TheftReportCommandLinkPersistenceEntity> findAllByTheftReportId(Long theftReportId);
}
