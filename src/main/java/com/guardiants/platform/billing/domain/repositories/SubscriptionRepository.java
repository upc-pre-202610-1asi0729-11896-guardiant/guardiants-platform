package com.guardiants.platform.billing.domain.repositories;

import com.guardiants.platform.billing.domain.model.aggregates.Subscription;
import java.util.Optional;

public interface SubscriptionRepository {
    Optional<Subscription> findById(Long id);
    Optional<Subscription> findByOwnerId(Long ownerId);
    Optional<Subscription> findByStripeSubscriptionId(String stripeId);
    Subscription save(Subscription subscription);
}
