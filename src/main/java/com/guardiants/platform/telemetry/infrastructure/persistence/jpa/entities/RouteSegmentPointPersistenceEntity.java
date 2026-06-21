package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "route_segment_points")
public class RouteSegmentPointPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_segment_id", nullable = false)
    private RouteSegmentPersistenceEntity routeSegment;

    private double lat;
    private double lng;

    private Instant recordedAt;

    private int sequenceIndex;
}
