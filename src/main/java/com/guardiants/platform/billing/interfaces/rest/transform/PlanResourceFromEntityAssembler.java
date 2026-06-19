package com.guardiants.platform.billing.interfaces.rest.transform;

import com.guardiants.platform.billing.domain.model.aggregates.Plan;
import com.guardiants.platform.billing.interfaces.rest.resources.PlanResource;

import java.util.Arrays;
import java.util.List;

public class PlanResourceFromEntityAssembler {

    public static PlanResource toResourceFromEntity(Plan plan) {
        List<String> features = Arrays.asList(
                plan.getFeaturesJson()
                        .replace("[", "").replace("]", "")
                        .replace("\"", "").split(","));
        return new PlanResource(
                plan.getId(), plan.getKey(), plan.getName(),
                plan.getPriceUsd(), plan.getBillingIntervalMonths(),
                plan.getMaxVehicles(), features);
    }
}
