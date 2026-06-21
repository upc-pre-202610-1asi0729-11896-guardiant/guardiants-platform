package com.guardiants.platform.query.domain.model.readmodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class DrivingReportView {
    private Long id;
    private Long vehicleId;
    private Instant periodStart;
    private Instant periodEnd;
    private double totalDistanceKm;
    private int totalDurationMinutes;
    private double averageSpeedKmh;
    private double drivingScore;
    private int harshBrakingEvents;
    private int harshAccelerationEvents;
    // riskPatterns added in feature 2
    public boolean hasData() { return vehicleId != null; }
}
