package com.guardiants.platform.telemetry.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.Instant;

@Embeddable
public class ConnectivityStatus {

    @Enumerated(EnumType.STRING)
    private ConnectivityValue value;

    private Instant lastSeenAt;

    protected ConnectivityStatus() {}

    public ConnectivityStatus(ConnectivityValue value, Instant lastSeenAt) {
        this.value = value;
        this.lastSeenAt = lastSeenAt;
    }

    public ConnectivityValue getValue() { return value; }
    public Instant getLastSeenAt() { return lastSeenAt; }

    public boolean isOnline() { return value == ConnectivityValue.ONLINE; }
    public boolean isJammed() { return value == ConnectivityValue.GPS_SIGNAL_LOST; }
}
