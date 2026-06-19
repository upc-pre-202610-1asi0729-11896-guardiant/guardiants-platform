package com.guardiants.platform.billing.interfaces.rest.resources;

import java.util.List;

public record PlanResource(
        Long id,
        String key,
        String name,
        double priceUsd,
        int billingIntervalMonths,
        int maxVehicles,
        List<String> features) {
}
