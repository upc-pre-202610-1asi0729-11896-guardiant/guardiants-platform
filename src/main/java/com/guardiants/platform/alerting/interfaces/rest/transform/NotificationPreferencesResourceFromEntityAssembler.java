package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.domain.model.entities.NotificationPreferences;
import com.guardiants.platform.alerting.interfaces.rest.resources.NotificationPreferencesResource;

public class NotificationPreferencesResourceFromEntityAssembler {

    public static NotificationPreferencesResource toResourceFromEntity(NotificationPreferences prefs) {
        return new NotificationPreferencesResource(
                prefs.getId(),
                prefs.getOwnerId(),
                prefs.isSecurityAlertsEnabled(),
                prefs.isLiveLocationEnabled(),
                prefs.isMaintenanceRemindersEnabled(),
                prefs.getUpdatedAt());
    }
}
