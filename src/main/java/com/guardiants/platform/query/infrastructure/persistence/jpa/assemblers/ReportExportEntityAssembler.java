package com.guardiants.platform.query.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.query.domain.model.readmodels.ExportFormat;
import com.guardiants.platform.query.domain.model.readmodels.ReportExport;
import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.ReportExportPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class ReportExportEntityAssembler {

    public ReportExportPersistenceEntity toPersistenceEntityFromDomain(ReportExport export) {
        var entity = new ReportExportPersistenceEntity();
        entity.setId(export.getId());
        entity.setSourceReportId(export.getSourceReportId());
        entity.setFormat(export.getFormat().name());
        entity.setGeneratedAt(export.getGeneratedAt());
        entity.setDownloadUrl(export.getDownloadUrl());
        return entity;
    }

    public ReportExport toDomainFromPersistenceEntity(ReportExportPersistenceEntity entity) {
        var export = new ReportExport(
                entity.getSourceReportId(),
                ExportFormat.valueOf(entity.getFormat()),
                entity.getDownloadUrl(),
                entity.getGeneratedAt());
        export.setId(entity.getId());
        return export;
    }
}
