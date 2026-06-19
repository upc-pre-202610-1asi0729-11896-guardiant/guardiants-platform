package com.guardiants.platform.billing.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "subscriptions")
public class SubscriptionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private Long ownerId;

    @Column(nullable = false)
    private Long planId;

    @Column(nullable = false, length = 15)
    private String status;

    private Instant currentPeriodStart;
    private Instant currentPeriodEnd;

    @Column(length = 200)
    private String stripeSubscriptionId;

    @Column(length = 200)
    private String stripeCustomerId;

    @Column(nullable = false)
    private boolean cancelAtPeriodEnd = false;
}