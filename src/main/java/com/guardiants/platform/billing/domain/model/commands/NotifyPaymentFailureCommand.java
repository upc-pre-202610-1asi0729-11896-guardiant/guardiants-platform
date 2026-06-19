package com.guardiants.platform.billing.domain.model.commands;

public record NotifyPaymentFailureCommand(Long subscriptionId, Long paymentId, Long ownerId) {
}
