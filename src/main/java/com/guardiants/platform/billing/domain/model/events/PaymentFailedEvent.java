package com.guardiants.platform.billing.domain.model.events;

// PaymentFailedEvent.java
public record PaymentFailedEvent(Long paymentId, Long subscriptionId, String failureReason) {}