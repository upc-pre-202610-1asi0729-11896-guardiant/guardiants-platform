package com.guardiants.platform.billing.domain.model.events;

// SubscriptionActivatedEvent.java
public record SubscriptionActivatedEvent(Long subscriptionId, Long ownerId, Long planId) {}
