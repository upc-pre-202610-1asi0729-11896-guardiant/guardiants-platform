package com.guardiants.platform.billing.interfaces.rest.transform;

import com.guardiants.platform.billing.domain.model.aggregates.Payment;
import com.guardiants.platform.billing.interfaces.rest.resources.PaymentResource;

public class PaymentResourceFromEntityAssembler {

    public static PaymentResource toResourceFromEntity(Payment payment) {
        return new PaymentResource(
                payment.getId(),
                payment.getSubscriptionId(),
                payment.getStripePaymentIntentId(),
                payment.getAmountUsd(),
                payment.getCurrency(),
                payment.getStatus().name(),
                payment.getFailureReason(),
                payment.getProcessedAt());
    }
}
