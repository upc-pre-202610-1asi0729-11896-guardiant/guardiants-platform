package com.guardiants.platform.fleet.interfaces.rest.transform;

import com.guardiants.platform.fleet.domain.model.commands.CreateAlertRuleCommand;
import com.guardiants.platform.fleet.domain.model.valueobjects.AlertRuleType;
import com.guardiants.platform.fleet.domain.model.valueobjects.Geofence;
import com.guardiants.platform.fleet.interfaces.rest.resources.CreateAlertRuleResource;

public class CreateAlertRuleCommandFromResourceAssembler {

    public static CreateAlertRuleCommand toCommandFromResource(CreateAlertRuleResource resource) {
        Geofence geofence = resource.geofence() != null
                ? new Geofence(resource.geofence().centerLat(),
                        resource.geofence().centerLng(),
                        resource.geofence().radiusMeters())
                : null;
        return new CreateAlertRuleCommand(resource.fleetId(), resource.vehicleId(),
                AlertRuleType.valueOf(resource.type()), geofence,
                resource.speedThresholdKmh(), resource.prolongedStopThresholdMinutes());
    }
}
