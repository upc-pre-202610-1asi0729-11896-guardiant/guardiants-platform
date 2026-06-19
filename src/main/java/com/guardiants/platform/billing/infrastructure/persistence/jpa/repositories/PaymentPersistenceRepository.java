package com.guardiants.platform.billing.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.billing.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentPersistenceRepository
        extends JpaRepository<PaymentPersistenceEntity, Long> {
    List<PaymentPersistenceEntity> findAllBySubscriptionId(Long subscriptionId);
    Optional<PaymentPersistenceEntity> findByStripePaymentIntentId(String id);
}