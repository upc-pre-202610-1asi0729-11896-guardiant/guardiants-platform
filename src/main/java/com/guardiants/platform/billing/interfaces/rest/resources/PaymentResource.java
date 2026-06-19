package com.guardiants.platform.billing.interfaces.rest.resources;

import java.time.Instant;

public record PaymentResource(
        Long id,
        Long subscriptionId,
        String stripePaymentIntentId,
        double amountUsd,
        String currency,
        String status,
        String failureReason,
        Instant processedAt) {
}
