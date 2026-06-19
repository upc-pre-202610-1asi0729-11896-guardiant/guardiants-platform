package com.guardiants.platform.billing.domain.model.aggregates;

import com.guardiants.platform.billing.domain.model.commands.NotifyPaymentFailureCommand;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "payment_failure_notifications")
public class PaymentFailureNotification extends AbstractDomainAggregateRoot<PaymentFailureNotification> {

    @Column(nullable = false)
    private Long subscriptionId;

    @Column(nullable = false)
    private Long paymentId;

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private Instant sentAt;

    @Column(nullable = false)
    private boolean acknowledged = false;

    public PaymentFailureNotification(NotifyPaymentFailureCommand command) {
        this.subscriptionId = command.subscriptionId();
        this.paymentId = command.paymentId();
        this.ownerId = command.ownerId();
        this.sentAt = Instant.now();
        this.acknowledged = false;
    }

    public static PaymentFailureNotification reconstitute(Long id, Long subscriptionId,
                                                           Long paymentId, Long ownerId,
                                                           Instant sentAt, boolean acknowledged) {
        var n = new PaymentFailureNotification();
        n.setId(id);
        n.subscriptionId = subscriptionId;
        n.paymentId = paymentId;
        n.ownerId = ownerId;
        n.sentAt = sentAt;
        n.acknowledged = acknowledged;
        return n;
    }

    public void acknowledge() { this.acknowledged = true; }

    public long daysUntilSuspension(int gracePeriodDays) {
        return java.time.temporal.ChronoUnit.DAYS.between(Instant.now(),
                sentAt.plus(gracePeriodDays, java.time.temporal.ChronoUnit.DAYS));
    }
}