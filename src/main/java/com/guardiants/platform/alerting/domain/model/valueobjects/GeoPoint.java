package com.guardiants.platform.alerting.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record GeoPoint(double lat, double lng) {
}
