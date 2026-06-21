package com.guardiants.platform.query.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "route_segment_views")
public class RouteSegmentViewPersistenceEntity extends AuditableAbstractPersistenceEntity {

    private Long segmentId;
    private Instant startedAt;
    private Instant endedAt;
    private double startLat;
    private double startLng;
    private double endLat;
    private double endLng;
    private double distanceKm;
    private int durationMinutes;
}
