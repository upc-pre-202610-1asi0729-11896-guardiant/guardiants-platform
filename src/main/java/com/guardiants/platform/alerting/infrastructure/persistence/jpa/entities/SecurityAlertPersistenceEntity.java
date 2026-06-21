package com.guardiants.platform.alerting.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "security_alerts")
public class SecurityAlertPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private Long vehicleId;

    private Long ruleId;

    @Column(nullable = false, length = 40)
    private String type;

    @Column(nullable = false, length = 10)
    private String severity;

    private double lat;
    private double lng;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Instant generatedAt;

    @Column(nullable = false, length = 15)
    private String status;

    private Instant acknowledgedAt;
    private Instant closedAt;
}
