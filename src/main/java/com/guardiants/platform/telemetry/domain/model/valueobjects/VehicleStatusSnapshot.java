package com.guardiants.platform.telemetry.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record VehicleStatusSnapshot(
        double speedKmh,
        Double fuelLevelPercent,
        Double engineTemperatureC,
        double batteryLevelPercent,
        Double odometerKm,
        Integer rpm,
        boolean engineOn) {
}
