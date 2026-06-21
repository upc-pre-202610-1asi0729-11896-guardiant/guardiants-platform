package com.guardiants.platform.telemetry.application.internal.outboundservices.mqtt;

public interface TelemetryIngestionPort {
    void onMessage(String topic, byte[] payload);
}
