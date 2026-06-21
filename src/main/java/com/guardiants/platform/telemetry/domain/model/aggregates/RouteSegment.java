package com.guardiants.platform.telemetry.domain.model.aggregates;

import com.guardiants.platform.telemetry.domain.model.valueobjects.GeoPoint;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "route_segments")
public class RouteSegment extends AbstractDomainAggregateRoot<RouteSegment> {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private Instant startedAt;

    private Instant endedAt;

    @Column(nullable = false)
    private double distanceKm = 0.0;

    @Transient
    private List<GeoPoint> points = new ArrayList<>();

    public RouteSegment(Long vehicleId, Instant startedAt) {
        this.vehicleId = vehicleId;
        this.startedAt = startedAt;
    }

    /** Reconstruction constructor used by persistence assemblers. */
    public RouteSegment(Long vehicleId, Instant startedAt, Instant endedAt, double distanceKm) {
        this.vehicleId = vehicleId;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
        this.distanceKm = distanceKm;
    }

    public void appendPoint(GeoPoint point) {
        this.points.add(point);
    }

    public void close(Instant endedAt) {
        this.endedAt = endedAt;
    }

    public long durationMinutes() {
        if (endedAt == null) return 0;
        return java.time.Duration.between(startedAt, endedAt).toMinutes();
    }

    public boolean isOngoing() { return endedAt == null; }
}
