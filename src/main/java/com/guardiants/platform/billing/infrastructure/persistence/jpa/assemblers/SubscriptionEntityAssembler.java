package com.guardiants.platform.billing.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.billing.domain.model.aggregates.Subscription;
import com.guardiants.platform.billing.domain.model.commands.SelectPlanCommand;
import com.guardiants.platform.billing.domain.model.valueobjects.SubscriptionStatus;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.entities.SubscriptionPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionEntityAssembler {

    public SubscriptionPersistenceEntity toPersistenceEntityFromDomain(Subscription sub) {
        var entity = new SubscriptionPersistenceEntity();
        entity.setId(sub.getId());
        entity.setOwnerId(sub.getOwnerId());
        entity.setPlanId(sub.getPlanId());
        entity.setStatus(sub.getStatus().name());
        entity.setCurrentPeriodStart(sub.getCurrentPeriodStart());
        entity.setCurrentPeriodEnd(sub.getCurrentPeriodEnd());
        entity.setStripeSubscriptionId(sub.getStripeSubscriptionId());
        entity.setStripeCustomerId(sub.getStripeCustomerId());
        entity.setCancelAtPeriodEnd(sub.isCancelAtPeriodEnd());
        return entity;
    }

    public Subscription toDomainFromPersistenceEntity(SubscriptionPersistenceEntity entity) {
        var sub = new Subscription(
                new SelectPlanCommand(entity.getOwnerId(), entity.getPlanId()));
        sub.setId(entity.getId());
        if (entity.getStripeSubscriptionId() != null) {
            sub.activate(entity.getStripeSubscriptionId(),
                    entity.getCurrentPeriodStart(), entity.getCurrentPeriodEnd());
        }
        if (SubscriptionStatus.valueOf(entity.getStatus()) == SubscriptionStatus.SUSPENDED)
            sub.suspend();
        if (SubscriptionStatus.valueOf(entity.getStatus()) == SubscriptionStatus.CANCELED)
            sub.cancel();
        return sub;
    }
}