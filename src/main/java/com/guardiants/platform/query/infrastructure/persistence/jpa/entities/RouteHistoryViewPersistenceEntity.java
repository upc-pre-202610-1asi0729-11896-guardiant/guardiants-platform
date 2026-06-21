package com.guardiants.platform.query.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "route_history_views")
public class RouteHistoryViewPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long vehicleId;

    private Instant periodStart;
    private Instant periodEnd;

    private double totalDistanceKm;
    private int totalDurationMinutes;
    private int totalTripsCount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "route_history_view_id")
    private List<RouteSegmentViewPersistenceEntity> segments = new ArrayList<>();
}
