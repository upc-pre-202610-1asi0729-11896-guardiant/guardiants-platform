package com.guardiants.platform.billing.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payment_failure_notifications")
public class PaymentFailureNotificationPersistenceEntity extends AuditableAbstractPersistenceEntity {

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
}