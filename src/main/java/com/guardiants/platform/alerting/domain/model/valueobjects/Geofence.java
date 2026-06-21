package com.guardiants.platform.alerting.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Geofence(double centerLat, double centerLng, double radiusMeters) {

    public Geofence {
        if (radiusMeters <= 0)
            throw new IllegalArgumentException("alerting.error.invalidGeofence");
    }

    public boolean contains(double lat, double lng) {
        double dLat = Math.toRadians(lat - centerLat);
        double dLng = Math.toRadians(lng - centerLng);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(centerLat))
                * Math.cos(Math.toRadians(lat))
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double distanceMeters = 6371000 * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return distanceMeters <= radiusMeters;
    }
}
