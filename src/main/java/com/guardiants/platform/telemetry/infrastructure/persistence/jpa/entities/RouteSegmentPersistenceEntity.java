package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "route_segments")
public class RouteSegmentPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private Instant startedAt;

    private Instant endedAt;

    @Column(nullable = false)
    private double distanceKm;
}
