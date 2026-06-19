package com.guardiants.platform.billing.interfaces.rest.transform;

import com.guardiants.platform.billing.domain.model.aggregates.Subscription;
import com.guardiants.platform.billing.interfaces.rest.resources.SubscriptionResource;

public class SubscriptionResourceFromEntityAssembler {

    public static SubscriptionResource toResourceFromEntity(Subscription sub) {
        return new SubscriptionResource(sub.getId(), sub.getOwnerId(), sub.getPlanId(),
                sub.getStatus().name(), sub.getCurrentPeriodStart(), sub.getCurrentPeriodEnd(),
                sub.getStripeSubscriptionId(), sub.getStripeCustomerId(),
                sub.isCancelAtPeriodEnd(), null, null);
    }
}