package com.guardiants.platform.billing.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.billing.domain.model.aggregates.Plan;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.entities.PlanPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class PlanEntityAssembler {

    public PlanPersistenceEntity toPersistenceEntityFromDomain(Plan plan) {
        var entity = new PlanPersistenceEntity();
        entity.setId(plan.getId());
        entity.setKey(plan.getKey());
        entity.setName(plan.getName());
        entity.setPriceUsd(plan.getPriceUsd());
        entity.setBillingIntervalMonths(plan.getBillingIntervalMonths());
        entity.setMaxVehicles(plan.getMaxVehicles());
        entity.setFeaturesJson(plan.getFeaturesJson());
        return entity;
    }

    public Plan toDomainFromPersistenceEntity(PlanPersistenceEntity entity) {
        var plan = new Plan(entity.getKey(), entity.getName(), entity.getPriceUsd(),
                entity.getBillingIntervalMonths(), entity.getMaxVehicles(),
                entity.getFeaturesJson());
        plan.setId(entity.getId());
        return plan;
    }
}