package com.guardiants.platform.telemetry.domain.model.aggregates;

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
    private List<Object> points = new ArrayList<>();

    public RouteSegment(Long vehicleId, Instant startedAt) {
        this.vehicleId = vehicleId;
        this.startedAt = startedAt;
    }

    public boolean isOngoing() { return endedAt == null; }
}
