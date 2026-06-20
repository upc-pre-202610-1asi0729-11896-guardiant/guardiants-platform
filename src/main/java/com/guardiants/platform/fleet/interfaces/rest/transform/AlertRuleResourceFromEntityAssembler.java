package com.guardiants.platform.fleet.interfaces.rest.transform;

import com.guardiants.platform.fleet.domain.model.aggregates.AlertRule;
import com.guardiants.platform.fleet.interfaces.rest.resources.AlertRuleResource;
import com.guardiants.platform.fleet.interfaces.rest.resources.GeofenceResource;

public class AlertRuleResourceFromEntityAssembler {

    public static AlertRuleResource toResourceFromEntity(AlertRule rule) {
        GeofenceResource geofence = rule.getGeofence() != null
                ? new GeofenceResource(rule.getGeofence().centerLat(),
                        rule.getGeofence().centerLng(),
                        rule.getGeofence().radiusMeters())
                : null;
        return new AlertRuleResource(rule.getId(), rule.getFleetId(), rule.getVehicleId(),
                rule.getType().name(), geofence, rule.getSpeedThresholdKmh(),
                rule.getProlongedStopThresholdMinutes(), rule.isEnabled());
    }
}
