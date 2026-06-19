// fleet/interfaces/acl/FleetContextFacade.java
package com.guardiants.platform.fleet.interfaces.acl;

import java.util.Optional;

/**
 * ACL facade that exposes Fleet capabilities to other bounded contexts.
 * Telemetry, Billing and Commands depend on this interface — never on Fleet internals.
 */
public interface FleetContextFacade {
    boolean existsVehicleById(Long vehicleId);
    Optional<Long> findVehicleIdByDeviceSerial(String deviceSerial);
    Optional<Long> findFleetIdByVehicleId(Long vehicleId);
    Optional<String> findVehiclePlateById(Long vehicleId);
}