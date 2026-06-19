package com.guardiants.platform.billing.application.internal.queryservices;

import com.guardiants.platform.billing.application.queryservices.SubscriptionQueryService;
import com.guardiants.platform.billing.domain.model.aggregates.Subscription;
import com.guardiants.platform.billing.domain.model.queries.GetCurrentSubscriptionQuery;
import com.guardiants.platform.billing.domain.model.queries.GetSubscriptionByIdQuery;
import com.guardiants.platform.billing.domain.model.queries.GetSubscriptionByStripeIdQuery;
import com.guardiants.platform.billing.domain.repositories.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionQueryServiceImpl implements SubscriptionQueryService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionQueryServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public Optional<Subscription> handle(GetCurrentSubscriptionQuery query) {
        return subscriptionRepository.findByOwnerId(query.ownerId());
    }

    @Override
    public Optional<Subscription> handle(GetSubscriptionByIdQuery query) {
        return subscriptionRepository.findById(query.subscriptionId());
    }

    @Override
    public Optional<Subscription> handle(GetSubscriptionByStripeIdQuery query) {
        return subscriptionRepository.findByStripeSubscriptionId(query.stripeSubscriptionId());
    }
}
