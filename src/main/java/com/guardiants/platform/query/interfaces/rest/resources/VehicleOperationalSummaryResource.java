package com.guardiants.platform.query.interfaces.rest.resources;

public record VehicleOperationalSummaryResource(
        Long vehicleId, String plate, double totalDistanceKm, int totalTripsCount,
        int alertsCount, int loansCount, Double drivingScore) {}
