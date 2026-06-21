package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.domain.model.commands.UpdateAlertRuleCommand;
import com.guardiants.platform.alerting.domain.model.valueobjects.Geofence;
import com.guardiants.platform.alerting.interfaces.rest.resources.UpdateAlertRuleResource;

public class UpdateAlertRuleCommandFromResourceAssembler {

    public static UpdateAlertRuleCommand toCommandFromResource(Long ruleId,
                                                               UpdateAlertRuleResource resource) {
        Geofence geofence = resource.geofence() != null
                ? new Geofence(resource.geofence().centerLat(),
                               resource.geofence().centerLng(),
                               resource.geofence().radiusMeters())
                : null;
        return new UpdateAlertRuleCommand(
                ruleId,
                resource.enabled(),
                geofence,
                resource.speedThresholdKmh(),
                resource.prolongedStopThresholdMinutes());
    }
}
