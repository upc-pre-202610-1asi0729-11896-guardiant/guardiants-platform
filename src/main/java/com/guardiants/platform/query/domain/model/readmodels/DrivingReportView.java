package com.guardiants.platform.query.domain.model.readmodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
    private List<RiskPatternView> riskPatterns = new ArrayList<>();
    public boolean hasData() { return vehicleId != null; }
}
