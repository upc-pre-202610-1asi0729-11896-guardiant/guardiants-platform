package com.guardiants.platform.billing.domain.repositories;

import com.guardiants.platform.billing.domain.model.aggregates.PaymentFailureNotification;
import java.util.Optional;

public interface PaymentFailureNotificationRepository {
    Optional<PaymentFailureNotification> findById(Long id);
    Optional<PaymentFailureNotification> findByOwnerId(Long ownerId);
    PaymentFailureNotification save(PaymentFailureNotification notification);
}