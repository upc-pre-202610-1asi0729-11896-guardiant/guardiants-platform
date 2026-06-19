package com.guardiants.platform.billing.domain.model.events;
// PlanSelectedEvent.java
public record PlanSelectedEvent(Long subscriptionId, Long ownerId, Long planId) {}