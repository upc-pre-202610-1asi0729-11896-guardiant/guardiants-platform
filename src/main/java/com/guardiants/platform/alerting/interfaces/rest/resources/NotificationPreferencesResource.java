package com.guardiants.platform.alerting.interfaces.rest.resources;

import java.time.Instant;

public record NotificationPreferencesResource(
        Long id,
        Long ownerId,
        boolean securityAlertsEnabled,
        boolean liveLocationEnabled,
        boolean maintenanceRemindersEnabled,
        Instant updatedAt) {
}
