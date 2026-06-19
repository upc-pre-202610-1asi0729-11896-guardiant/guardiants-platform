package com.guardiants.platform.billing.interfaces.rest.resources;

import java.time.Instant;

public record PaymentFailureNotificationResource(
        Long id,
        Long subscriptionId,
        Long paymentId,
        Long ownerId,
        Instant sentAt,
        boolean acknowledged) {
}
