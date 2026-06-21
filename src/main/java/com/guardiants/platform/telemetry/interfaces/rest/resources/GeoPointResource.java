package com.guardiants.platform.telemetry.interfaces.rest.resources;

import java.time.Instant;

public record GeoPointResource(double lat, double lng, Instant recordedAt) {
}
