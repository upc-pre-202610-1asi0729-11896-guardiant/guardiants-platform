package com.guardiants.platform.telemetry.infrastructure.acl;

import com.guardiants.platform.fleet.interfaces.acl.FleetContextFacade;
import com.guardiants.platform.telemetry.application.internal.outboundservices.acl.FleetVehicleService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
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
    public Optional<Long> findDeviceAssignmentByDeviceSerial(String deviceSerial) {
        return fleetContextFacade.findVehicleIdByDeviceSerial(deviceSerial);
    }
}
