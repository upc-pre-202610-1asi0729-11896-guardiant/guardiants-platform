package com.guardiants.platform.billing.interfaces.rest.resources;

import java.time.Instant;

public record DeviceConnectionStatusResource(String value, Instant lastSeenAt) {
}
