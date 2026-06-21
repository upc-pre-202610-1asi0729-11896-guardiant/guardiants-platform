package com.guardiants.platform.commands.interfaces.rest.transform;

import com.guardiants.platform.commands.domain.model.aggregates.TheftReport;
import com.guardiants.platform.commands.interfaces.rest.resources.TheftReportResource;

public class TheftReportResourceFromEntityAssembler {

    public static TheftReportResource toResourceFromEntity(TheftReport report) {
        return new TheftReportResource(report.getId(), report.getVehicleId(),
                report.getReportedByUserId(), report.getReportedAt(),
                report.getStatus().name(), report.getRelatedCommandIds(),
                report.getRelatedAlertId());
    }
}
