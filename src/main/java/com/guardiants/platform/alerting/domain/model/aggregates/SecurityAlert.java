package com.guardiants.platform.alerting.domain.model.aggregates;

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
}
