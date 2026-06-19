package com.guardiants.platform.billing.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class PaymentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long subscriptionId;

    @Column(length = 200)
    private String stripePaymentIntentId;

    private double amountUsd;

    @Column(length = 10)
    private String currency;

    @Column(nullable = false, length = 15)
    private String status;

    @Column(length = 500)
    private String failureReason;

    private Instant processedAt;
}