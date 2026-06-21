package com.guardiants.platform.alerting.domain.model.queries;

import com.guardiants.platform.alerting.domain.model.valueobjects.AlertFilterCategory;
import com.guardiants.platform.alerting.domain.model.valueobjects.AlertPeriod;

public record GetSecurityAlertsByOwnerIdQuery(
        Long ownerId,
        AlertFilterCategory category,
        AlertPeriod period) {
}
