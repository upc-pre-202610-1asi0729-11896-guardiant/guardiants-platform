package com.guardiants.platform.commands.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.commands.domain.model.aggregates.TheftReport;
import com.guardiants.platform.commands.domain.model.valueobjects.IncidentStatus;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.entities.TheftReportPersistenceEntity;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.repositories.TheftReportCommandLinkPersistenceRepository;
import org.springframework.stereotype.Component;

@Component
public class TheftReportEntityAssembler {

    private final TheftReportCommandLinkPersistenceRepository linkRepository;

    public TheftReportEntityAssembler(TheftReportCommandLinkPersistenceRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public TheftReportPersistenceEntity toPersistenceEntityFromDomain(TheftReport report) {
        var entity = new TheftReportPersistenceEntity();
        entity.setId(report.getId());
        entity.setVehicleId(report.getVehicleId());
        entity.setReportedByUserId(report.getReportedByUserId());
        entity.setReportedAt(report.getReportedAt());
        entity.setStatus(report.getStatus().name());
        entity.setRelatedAlertId(report.getRelatedAlertId());
        return entity;
    }

    public TheftReport toDomainFromPersistenceEntity(TheftReportPersistenceEntity entity) {
        var report = new TheftReport(
                entity.getVehicleId(),
                entity.getReportedByUserId(),
                entity.getReportedAt(),
                IncidentStatus.valueOf(entity.getStatus()),
                entity.getRelatedAlertId());
        report.setId(entity.getId());
        linkRepository.findAllByTheftReportId(entity.getId())
                .forEach(link -> report.linkCommand(link.getCommandId()));
        return report;
    }
}
