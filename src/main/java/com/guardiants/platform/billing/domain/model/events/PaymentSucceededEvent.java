package com.guardiants.platform.billing.domain.model.events;

// PaymentSucceededEvent.java
public record PaymentSucceededEvent(Long paymentId, Long subscriptionId) {}