package com.guardiants.platform.billing.domain.model.aggregates;

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

    public void acknowledge() { this.acknowledged = true; }

    public long daysUntilSuspension(int gracePeriodDays) {
        return java.time.temporal.ChronoUnit.DAYS.between(Instant.now(),
                sentAt.plus(gracePeriodDays, java.time.temporal.ChronoUnit.DAYS));
    }
}