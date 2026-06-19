package com.guardiants.platform.billing.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.billing.domain.model.aggregates.Payment;
import com.guardiants.platform.billing.domain.model.commands.ProcessPaymentCommand;
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
        entity.setStatus(payment.getStatus().name());
        entity.setFailureReason(payment.getFailureReason());
        entity.setProcessedAt(payment.getProcessedAt());
        return entity;
    }

    public Payment toDomainFromPersistenceEntity(PaymentPersistenceEntity entity) {
        var payment = new Payment(new ProcessPaymentCommand(
                entity.getSubscriptionId(), entity.getAmountUsd(),
                entity.getCurrency() != null ? entity.getCurrency() : "USD"));
        payment.setId(entity.getId());
        payment.setStripePaymentIntentId(entity.getStripePaymentIntentId());
        if (PaymentStatus.SUCCEEDED.name().equals(entity.getStatus())) payment.markSucceeded();
        if (PaymentStatus.FAILED.name().equals(entity.getStatus()))
            payment.markFailed(entity.getFailureReason());
        return payment;
    }
}