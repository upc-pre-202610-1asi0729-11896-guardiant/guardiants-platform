package com.guardiants.platform.billing.domain.model.events;

// SubscriptionExpiredEvent.java
public record SubscriptionExpiredEvent(Long subscriptionId, Long ownerId) {}