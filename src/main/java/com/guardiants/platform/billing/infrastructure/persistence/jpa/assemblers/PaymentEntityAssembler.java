package com.guardiants.platform.billing.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.billing.domain.model.aggregates.Payment;
import com.guardiants.platform.billing.domain.model.valueobjects.PaymentStatus;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.entities.PaymentPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentEntityAssembler {

    public PaymentPersistenceEntity toPersistenceEntityFromDomain(Payment payment) {
        var entity = new PaymentPersistenceEntity();
        entity.setId(payment.getId());
        entity.setSubscriptionId(payment.getSubscriptionId());
        entity.setStripePaymentIntentId(payment.getStripePaymentIntentId());
        entity.setAmountUsd(payment.getAmountUsd());
        entity.setCurrency(payment.getCurrency());
        entity.setStatus(payment.getStatus() != null ? payment.getStatus().name() : PaymentStatus.PENDING.name());
        entity.setFailureReason(payment.getFailureReason());
        entity.setProcessedAt(payment.getProcessedAt());
        return entity;
    }

    public Payment toDomainFromPersistenceEntity(PaymentPersistenceEntity entity) {
        var payment = Payment.reconstitute(
                entity.getId(),
                entity.getSubscriptionId(),
                entity.getStripePaymentIntentId(),
                entity.getAmountUsd(),
                entity.getCurrency() != null ? entity.getCurrency() : "USD",
                PaymentStatus.valueOf(entity.getStatus()),
                entity.getFailureReason(),
                entity.getProcessedAt());
        return payment;
    }
}