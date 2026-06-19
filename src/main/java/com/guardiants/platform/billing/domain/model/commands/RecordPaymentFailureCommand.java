package com.guardiants.platform.billing.domain.model.commands;

public record RecordPaymentFailureCommand(
        Long subscriptionId,
        String stripePaymentIntentId,
        String failureReason) {
}
