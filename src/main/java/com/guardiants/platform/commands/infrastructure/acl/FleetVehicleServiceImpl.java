package com.guardiants.platform.commands.infrastructure.acl;

import com.guardiants.platform.commands.application.internal.outboundservices.acl.FleetVehicleService;
import com.guardiants.platform.fleet.interfaces.acl.FleetContextFacade;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Named distinctly from the Telemetry BC's {@code FleetVehicleServiceImpl} to avoid a
 * duplicate Spring bean definition.
 */
@Service("commandsFleetVehicleServiceImpl")
public class FleetVehicleServiceImpl implements FleetVehicleService {

    private final FleetContextFacade fleetContextFacade;

    public FleetVehicleServiceImpl(FleetContextFacade fleetContextFacade) {
        this.fleetContextFacade = fleetContextFacade;
    }

    @Override
    public boolean existsVehicleById(Long vehicleId) {
        return fleetContextFacade.existsVehicleById(vehicleId);
    }

    @Override
    public Optional<Long> findFleetIdByVehicleId(Long vehicleId) {
        return fleetContextFacade.findFleetIdByVehicleId(vehicleId);
    }
}
