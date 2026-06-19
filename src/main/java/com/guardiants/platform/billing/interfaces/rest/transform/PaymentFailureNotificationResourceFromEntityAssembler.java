package com.guardiants.platform.billing.interfaces.rest.transform;

import com.guardiants.platform.billing.domain.model.aggregates.PaymentFailureNotification;
import com.guardiants.platform.billing.interfaces.rest.resources.PaymentFailureNotificationResource;

public class PaymentFailureNotificationResourceFromEntityAssembler {

    public static PaymentFailureNotificationResource toResourceFromEntity(
            PaymentFailureNotification notification) {
        return new PaymentFailureNotificationResource(
                notification.getId(),
                notification.getSubscriptionId(),
                notification.getPaymentId(),
                notification.getOwnerId(),
                notification.getSentAt(),
                notification.isAcknowledged());
    }
}
