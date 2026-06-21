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
@Table(name = "driving_report_views")
public class DrivingReportViewPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long vehicleId;

    private Instant periodStart;
    private Instant periodEnd;
    private double totalDistanceKm;
    private int totalDurationMinutes;
    private double averageSpeedKmh;
    private double drivingScore;
    private int harshBrakingEvents;
    private int harshAccelerationEvents;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "driving_report_view_id")
    private List<RiskPatternViewPersistenceEntity> riskPatterns = new ArrayList<>();
}
