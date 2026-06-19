package com.guardiants.platform.billing.domain.model.commands;

public record ProcessPaymentCommand(Long subscriptionId, double amountUsd, String currency) {
}
