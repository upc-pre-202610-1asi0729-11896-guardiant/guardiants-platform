package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import com.guardiants.platform.alerting.interfaces.rest.resources.GeoPointResource;
import com.guardiants.platform.alerting.interfaces.rest.resources.SecurityAlertResource;

public class SecurityAlertResourceFromEntityAssembler {

    public static SecurityAlertResource toResourceFromEntity(SecurityAlert alert) {
        GeoPointResource location = alert.getLocation() != null
                ? new GeoPointResource(alert.getLocation().lat(), alert.getLocation().lng())
                : null;
        return new SecurityAlertResource(
                alert.getId(),
                alert.getOwnerId(),
                alert.getVehicleId(),
                alert.getRuleId(),
                alert.getType().name(),
                alert.getSeverity().name(),
                location,
                alert.getDescription(),
                alert.getGeneratedAt(),
                alert.getStatus().name(),
                alert.getAcknowledgedAt(),
                alert.getClosedAt());
    }
}
