package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.domain.model.aggregates.AlertRule;
import com.guardiants.platform.alerting.interfaces.rest.resources.AlertRuleResource;
import com.guardiants.platform.alerting.interfaces.rest.resources.GeofenceResource;

public class AlertRuleResourceFromEntityAssembler {

    public static AlertRuleResource toResourceFromEntity(AlertRule rule) {
        GeofenceResource geofence = rule.getGeofence() != null
                ? new GeofenceResource(rule.getGeofence().centerLat(),
                                       rule.getGeofence().centerLng(),
                                       rule.getGeofence().radiusMeters())
                : null;
        return new AlertRuleResource(
                rule.getId(),
                rule.getOwnerId(),
                rule.getVehicleId(),
                rule.getType().name(),
                geofence,
                rule.getSpeedThresholdKmh(),
                rule.getProlongedStopThresholdMinutes(),
                rule.isEnabled());
    }
}
