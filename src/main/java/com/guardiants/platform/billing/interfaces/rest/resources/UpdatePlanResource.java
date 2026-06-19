package com.guardiants.platform.billing.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record UpdatePlanResource(@NotNull Long newPlanId) {
}
