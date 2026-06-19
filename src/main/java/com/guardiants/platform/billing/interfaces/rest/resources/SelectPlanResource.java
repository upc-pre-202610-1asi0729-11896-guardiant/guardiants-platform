package com.guardiants.platform.billing.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record SelectPlanResource(
        @NotNull Long ownerId,
        @NotNull Long planId) {}