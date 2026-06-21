package com.guardiants.platform.commands.interfaces.rest.transform;

import com.guardiants.platform.commands.domain.model.commands.ReportTheftCommand;
import com.guardiants.platform.commands.interfaces.rest.resources.ReportTheftResource;

public class ReportTheftCommandFromResourceAssembler {

    public static ReportTheftCommand toCommandFromResource(ReportTheftResource resource) {
        return new ReportTheftCommand(resource.vehicleId(), resource.reportedByUserId());
    }
}
