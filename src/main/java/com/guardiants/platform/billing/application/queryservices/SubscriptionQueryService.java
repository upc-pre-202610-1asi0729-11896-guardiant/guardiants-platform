package com.guardiants.platform.billing.application.queryservices;

import com.guardiants.platform.billing.domain.model.aggregates.Subscription;
import com.guardiants.platform.billing.domain.model.queries.GetCurrentSubscriptionQuery;
import com.guardiants.platform.billing.domain.model.queries.GetSubscriptionByIdQuery;
import com.guardiants.platform.billing.domain.model.queries.GetSubscriptionByStripeIdQuery;

import java.util.Optional;

public interface SubscriptionQueryService {
    Optional<Subscription> handle(GetCurrentSubscriptionQuery query);
    Optional<Subscription> handle(GetSubscriptionByIdQuery query);
    Optional<Subscription> handle(GetSubscriptionByStripeIdQuery query);
}
