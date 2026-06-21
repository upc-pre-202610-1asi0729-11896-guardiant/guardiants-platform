package com.guardiants.platform.query.interfaces.rest.transform;

import com.guardiants.platform.query.domain.model.readmodels.ReportExport;
import com.guardiants.platform.query.interfaces.rest.resources.ReportExportResource;

public class ReportExportResourceFromEntityAssembler {

    public static ReportExportResource toResourceFromEntity(ReportExport export) {
        return new ReportExportResource(
                export.getId(), export.getSourceReportId(),
                export.getFormat().name(), export.getGeneratedAt(), export.getDownloadUrl());
    }
}
