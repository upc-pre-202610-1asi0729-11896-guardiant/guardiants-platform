package com.guardiants.platform.billing.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.billing.domain.model.aggregates.PaymentFailureNotification;
import com.guardiants.platform.billing.domain.model.commands.NotifyPaymentFailureCommand;
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
        var notification = new PaymentFailureNotification(
                new NotifyPaymentFailureCommand(
                        entity.getSubscriptionId(), entity.getPaymentId(), entity.getOwnerId()));
        notification.setId(entity.getId());
        if (entity.isAcknowledged()) notification.acknowledge();
        return notification;
    }
}