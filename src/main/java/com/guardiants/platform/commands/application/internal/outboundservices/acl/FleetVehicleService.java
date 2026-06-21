package com.guardiants.platform.commands.application.internal.outboundservices.acl;

import java.util.Optional;

public interface FleetVehicleService {
    boolean existsVehicleById(Long vehicleId);
    Optional<Long> findFleetIdByVehicleId(Long vehicleId);
}
