package com.guardiants.platform.telemetry.application.internal.outboundservices.acl;

import java.util.Optional;

public interface FleetVehicleService {
    boolean existsVehicleById(Long vehicleId);
    Optional<Long> findDeviceAssignmentByDeviceSerial(String deviceSerial);
}
