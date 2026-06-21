package com.guardiants.platform.alerting.interfaces.rest.resources;

public record UpdateNotificationPreferencesResource(
        Boolean securityAlertsEnabled,
        Boolean liveLocationEnabled,
        Boolean maintenanceRemindersEnabled) {
}
