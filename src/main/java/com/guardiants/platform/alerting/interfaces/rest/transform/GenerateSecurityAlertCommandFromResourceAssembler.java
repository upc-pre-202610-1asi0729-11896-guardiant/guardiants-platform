package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.domain.model.commands.GenerateSecurityAlertCommand;
import com.guardiants.platform.alerting.domain.model.valueobjects.AlertSeverity;
import com.guardiants.platform.alerting.domain.model.valueobjects.AlertType;
import com.guardiants.platform.alerting.domain.model.valueobjects.GeoPoint;
import com.guardiants.platform.alerting.interfaces.rest.resources.GenerateSecurityAlertResource;

public class GenerateSecurityAlertCommandFromResourceAssembler {

    public static GenerateSecurityAlertCommand toCommandFromResource(
            GenerateSecurityAlertResource resource) {
        return new GenerateSecurityAlertCommand(
                resource.ownerId(),
                resource.vehicleId(),
                resource.ruleId(),
                AlertType.valueOf(resource.type()),
                AlertSeverity.valueOf(resource.severity()),
                new GeoPoint(resource.lat(), resource.lng()),
                resource.description());
    }
}
