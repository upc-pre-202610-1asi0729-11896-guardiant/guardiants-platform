package com.guardiants.platform.billing.domain.model.commands;

public record ConfirmPaymentCommand(Long subscriptionId, String stripePaymentIntentId) {
}
