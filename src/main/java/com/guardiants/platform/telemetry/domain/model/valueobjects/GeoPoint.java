package com.guardiants.platform.telemetry.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record GeoPoint(double lat, double lng) {
}
