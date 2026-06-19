package com.guardiants.platform.billing.domain.model.entities;

import com.guardiants.platform.billing.domain.model.valueobjects.ConnectionValue;
import java.time.Instant;

public class DeviceConnectionStatus {
    private Long ownerId;
    private ConnectionValue value;
    private Instant lastSeenAt;

    public DeviceConnectionStatus(Long ownerId, ConnectionValue value, Instant lastSeenAt) {
        this.ownerId = ownerId;
        this.value = value;
        this.lastSeenAt = lastSeenAt;
    }

    public Long getOwnerId()         { return ownerId; }
    public ConnectionValue getValue() { return value; }
    public Instant getLastSeenAt()   { return lastSeenAt; }
    public boolean isConnected()     { return value == ConnectionValue.CONNECTED; }
}