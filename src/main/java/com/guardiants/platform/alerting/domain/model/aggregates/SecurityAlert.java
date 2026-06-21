package com.guardiants.platform.alerting.domain.model.aggregates;

import com.guardiants.platform.alerting.domain.model.commands.GenerateSecurityAlertCommand;
import com.guardiants.platform.alerting.domain.model.valueobjects.*;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "security_alerts")
public class SecurityAlert extends AbstractDomainAggregateRoot<SecurityAlert> {

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private Long vehicleId;

    private Long ruleId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private AlertType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private AlertSeverity severity;

    @Embedded
    private GeoPoint location;

    @Column(nullable = false, length = 500)
    private String description;

    @Column(nullable = false)
    private Instant generatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private AlertStatus status;

    private Instant acknowledgedAt;
    private Instant closedAt;

    /** Constructor invoked by GenerateSecurityAlertCommand. */
    public SecurityAlert(GenerateSecurityAlertCommand command) {
        this.ownerId = command.ownerId();
        this.vehicleId = command.vehicleId();
        this.ruleId = command.ruleId();
        this.type = command.type();
        this.severity = command.severity();
        this.location = command.location();
        this.description = command.description();
        this.generatedAt = Instant.now();
        this.status = AlertStatus.GENERATED;
    }

    /** Reconstruction constructor used by persistence assemblers. */
    public SecurityAlert(Long ownerId, Long vehicleId, Long ruleId, AlertType type,
                         AlertSeverity severity, GeoPoint location, String description,
                         Instant generatedAt, AlertStatus status,
                         Instant acknowledgedAt, Instant closedAt) {
        this.ownerId = ownerId;
        this.vehicleId = vehicleId;
        this.ruleId = ruleId;
        this.type = type;
        this.severity = severity;
        this.location = location;
        this.description = description;
        this.generatedAt = generatedAt;
        this.status = status;
        this.acknowledgedAt = acknowledgedAt;
        this.closedAt = closedAt;
    }

    public void acknowledge() {
        if (!canBeAcknowledged())
            throw new IllegalStateException("alerting.error.invalidStatusTransition");
        this.status = AlertStatus.ACKNOWLEDGED;
        this.acknowledgedAt = Instant.now();
    }

    public void close() {
        if (!canBeClosed())
            throw new IllegalStateException("alerting.error.invalidStatusTransition");
        this.status = AlertStatus.CLOSED;
        this.closedAt = Instant.now();
    }

    public boolean isUnread() { return status == AlertStatus.GENERATED; }
    public boolean isUrgent() { return severity == AlertSeverity.CRITICAL; }
    public boolean canBeAcknowledged() { return status == AlertStatus.GENERATED; }
    public boolean canBeClosed() { return status == AlertStatus.ACKNOWLEDGED; }
}
