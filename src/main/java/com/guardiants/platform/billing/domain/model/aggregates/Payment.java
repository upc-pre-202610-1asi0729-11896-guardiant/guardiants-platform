package com.guardiants.platform.billing.domain.model.aggregates;

import com.guardiants.platform.billing.domain.model.valueobjects.PaymentStatus;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment extends AbstractDomainAggregateRoot<Payment> {

    @Column(nullable = false)
    private Long subscriptionId;

    @Column(length = 200)
    private String stripePaymentIntentId;

    private double amountUsd;

    @Column(length = 10)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private PaymentStatus status;

    @Column(length = 500)
    private String failureReason;

    private Instant processedAt;

    public static Payment reconstitute(Long id, Long subscriptionId, String stripePaymentIntentId,
                                        double amountUsd, String currency, PaymentStatus status,
                                        String failureReason, Instant processedAt) {
        var payment = new Payment();
        payment.setId(id);
        payment.subscriptionId = subscriptionId;
        payment.stripePaymentIntentId = stripePaymentIntentId;
        payment.amountUsd = amountUsd;
        payment.currency = currency;
        payment.status = status;
        payment.failureReason = failureReason;
        payment.processedAt = processedAt;
        return payment;
    }

    public void markSucceeded()           { this.status = PaymentStatus.SUCCEEDED; }
    public void markFailed(String reason) { this.status = PaymentStatus.FAILED; this.failureReason = reason; }
    public boolean isSuccessful()         { return status == PaymentStatus.SUCCEEDED; }
    public boolean isFailed()             { return status == PaymentStatus.FAILED; }
}