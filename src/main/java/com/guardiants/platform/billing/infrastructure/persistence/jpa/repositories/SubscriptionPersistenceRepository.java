package com.guardiants.platform.billing.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.billing.infrastructure.persistence.jpa.entities.SubscriptionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SubscriptionPersistenceRepository
        extends JpaRepository<SubscriptionPersistenceEntity, Long> {
    Optional<SubscriptionPersistenceEntity> findByOwnerId(Long ownerId);
    Optional<SubscriptionPersistenceEntity> findByStripeSubscriptionId(String stripeId);
}