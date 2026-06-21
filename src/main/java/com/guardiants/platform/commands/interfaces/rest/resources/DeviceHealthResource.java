package com.guardiants.platform.commands.interfaces.rest.resources;

import java.time.Instant;

public record DeviceHealthResource(Long vehicleId, String deviceSerial, String model,
        String imei, String status, Instant lastConnectionAt,
        Double batteryLevelPercent) {}
