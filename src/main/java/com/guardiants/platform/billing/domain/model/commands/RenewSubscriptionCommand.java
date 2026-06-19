package com.guardiants.platform.billing.domain.model.commands;

import java.time.Instant;

public record RenewSubscriptionCommand(Long subscriptionId, Instant newPeriodEnd) {
}
