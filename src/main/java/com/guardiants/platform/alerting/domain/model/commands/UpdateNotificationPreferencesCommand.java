package com.guardiants.platform.alerting.domain.model.commands;

public record UpdateNotificationPreferencesCommand(
        Long ownerId,
        Boolean securityAlertsEnabled,
        Boolean liveLocationEnabled,
        Boolean maintenanceRemindersEnabled) {
}
