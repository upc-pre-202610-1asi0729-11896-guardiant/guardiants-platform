package com.guardiants.platform.billing.domain.model.events;

// SubscriptionSuspendedEvent.java
public record SubscriptionSuspendedEvent(Long subscriptionId, Long ownerId) {}