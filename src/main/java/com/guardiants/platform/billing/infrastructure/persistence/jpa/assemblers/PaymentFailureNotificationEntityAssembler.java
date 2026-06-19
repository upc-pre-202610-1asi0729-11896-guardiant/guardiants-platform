package com.guardiants.platform.billing.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.billing.domain.model.aggregates.PaymentFailureNotification;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.entities.PaymentFailureNotificationPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentFailureNotificationEntityAssembler {

    public PaymentFailureNotificationPersistenceEntity toPersistenceEntityFromDomain(
            PaymentFailureNotification notification) {
        var entity = new PaymentFailureNotificationPersistenceEntity();
        entity.setId(notification.getId());
        entity.setSubscriptionId(notification.getSubscriptionId());
        entity.setPaymentId(notification.getPaymentId());
        entity.setOwnerId(notification.getOwnerId());
        entity.setSentAt(notification.getSentAt());
        entity.setAcknowledged(notification.isAcknowledged());
        return entity;
    }

    public PaymentFailureNotification toDomainFromPersistenceEntity(
            PaymentFailureNotificationPersistenceEntity entity) {
        return PaymentFailureNotification.reconstitute(
                entity.getId(),
                entity.getSubscriptionId(),
                entity.getPaymentId(),
                entity.getOwnerId(),
                entity.getSentAt(),
                entity.isAcknowledged());
    }
}
