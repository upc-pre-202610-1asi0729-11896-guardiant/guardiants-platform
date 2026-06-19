package com.guardiants.platform.billing.interfaces.rest.resources;

import java.time.Instant;

public record SubscriptionResource(
        Long id, Long ownerId, Long planId, String status,
        Instant currentPeriodStart, Instant currentPeriodEnd,
        String stripeSubscriptionId, String stripeCustomerId,
        boolean cancelAtPeriodEnd, Instant createdAt, Instant updatedAt) {}