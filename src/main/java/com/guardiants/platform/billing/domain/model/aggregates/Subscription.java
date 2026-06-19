package com.guardiants.platform.billing.domain.model.aggregates;

import com.guardiants.platform.billing.domain.model.commands.SelectPlanCommand;
import com.guardiants.platform.billing.domain.model.valueobjects.SubscriptionStatus;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription extends AbstractDomainAggregateRoot<Subscription> {

    @Column(nullable = false, unique = true)
    private Long ownerId;

    @Column(nullable = false)
    private Long planId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private SubscriptionStatus status;

    private Instant currentPeriodStart;
    private Instant currentPeriodEnd;

    @Column(length = 200)
    private String stripeSubscriptionId;

    @Column(length = 200)
    private String stripeCustomerId;

    @Column(nullable = false)
    private boolean cancelAtPeriodEnd = false;

    /** Constructor invoked by SelectPlanCommand. Status starts as PENDING. */
    public Subscription(SelectPlanCommand command) {
        this.ownerId = command.ownerId();
        this.planId = command.planId();
        this.status = SubscriptionStatus.PENDING;
    }

    public void activate(String stripeSubscriptionId, Instant start, Instant end) {
        this.stripeSubscriptionId = stripeSubscriptionId;
        this.currentPeriodStart = start;
        this.currentPeriodEnd = end;
        this.status = SubscriptionStatus.ACTIVE;
    }

    public void suspend() {
        if (status != SubscriptionStatus.ACTIVE)
            throw new IllegalStateException("billing.error.subscriptionNotFound");
        this.status = SubscriptionStatus.SUSPENDED;
    }

    public void expire() { this.status = SubscriptionStatus.EXPIRED; }

    public void renew(Instant newEnd) {
        this.currentPeriodEnd = newEnd;
        this.status = SubscriptionStatus.ACTIVE;
    }

    public void cancel() {
        this.cancelAtPeriodEnd = true;
        this.status = SubscriptionStatus.CANCELED;
    }

    public void updatePlan(Long planId) { this.planId = planId; }

    public boolean isActive()    { return status == SubscriptionStatus.ACTIVE; }
    public boolean isExpired()   { return status == SubscriptionStatus.EXPIRED; }
    public boolean isSuspended() { return status == SubscriptionStatus.SUSPENDED; }

    public long daysUntilExpiration() {
        if (currentPeriodEnd == null) return 0;
        return ChronoUnit.DAYS.between(Instant.now(), currentPeriodEnd);
    }
}