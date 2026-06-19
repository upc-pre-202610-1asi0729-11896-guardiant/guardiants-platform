package com.guardiants.platform.billing.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConfirmPaymentResource(
        @NotNull Long subscriptionId,
        @NotBlank String stripePaymentIntentId) {
}
