package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.domain.model.commands.CreateAlertRuleCommand;
import com.guardiants.platform.alerting.domain.model.valueobjects.AlertRuleType;
import com.guardiants.platform.alerting.domain.model.valueobjects.Geofence;
import com.guardiants.platform.alerting.interfaces.rest.resources.CreateAlertRuleResource;

public class CreateAlertRuleCommandFromResourceAssembler {

    public static CreateAlertRuleCommand toCommandFromResource(CreateAlertRuleResource resource) {
        Geofence geofence = resource.geofence() != null
                ? new Geofence(resource.geofence().centerLat(),
                               resource.geofence().centerLng(),
                               resource.geofence().radiusMeters())
                : null;
        return new CreateAlertRuleCommand(
                resource.ownerId(),
                resource.vehicleId(),
                AlertRuleType.valueOf(resource.type()),
                geofence,
                resource.speedThresholdKmh(),
                resource.prolongedStopThresholdMinutes());
    }
}
