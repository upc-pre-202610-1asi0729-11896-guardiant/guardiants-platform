package com.guardiants.platform.billing.domain.model.commands;

import java.time.Instant;

public record ActivateSubscriptionCommand(
        Long subscriptionId,
        String stripeSubscriptionId,
        Instant periodStart,
        Instant periodEnd) {
}
