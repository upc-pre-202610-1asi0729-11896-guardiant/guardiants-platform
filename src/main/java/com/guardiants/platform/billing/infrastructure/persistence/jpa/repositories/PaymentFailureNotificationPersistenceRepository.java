package com.guardiants.platform.billing.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.billing.infrastructure.persistence.jpa.entities.PaymentFailureNotificationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentFailureNotificationPersistenceRepository
        extends JpaRepository<PaymentFailureNotificationPersistenceEntity, Long> {
    Optional<PaymentFailureNotificationPersistenceEntity> findByOwnerId(Long ownerId);
}