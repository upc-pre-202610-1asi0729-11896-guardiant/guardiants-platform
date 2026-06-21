package com.guardiants.platform.query.domain.model.readmodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehicleOperationalSummaryView {
    private Long vehicleId;
    private String plate;
    private double totalDistanceKm;
    private int totalTripsCount;
    private int alertsCount;
    private int loansCount;
    private Double drivingScore;
}
