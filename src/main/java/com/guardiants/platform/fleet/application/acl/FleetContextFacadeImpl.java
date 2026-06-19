package com.guardiants.platform.fleet.application.acl;

import com.guardiants.platform.fleet.application.queryservices.VehicleQueryService;
import com.guardiants.platform.fleet.domain.model.queries.ExistsVehicleByIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetVehicleByIdQuery;
import com.guardiants.platform.fleet.domain.repositories.DeviceAssignmentRepository;
import com.guardiants.platform.fleet.interfaces.acl.FleetContextFacade;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class FleetContextFacadeImpl implements FleetContextFacade {

    private final VehicleQueryService vehicleQueryService;
    private final DeviceAssignmentRepository deviceAssignmentRepository;

    public FleetContextFacadeImpl(VehicleQueryService vehicleQueryService,
                                  DeviceAssignmentRepository deviceAssignmentRepository) {
        this.vehicleQueryService = vehicleQueryService;
        this.deviceAssignmentRepository = deviceAssignmentRepository;
    }

    @Override
    public boolean existsVehicleById(Long vehicleId) {
        return vehicleQueryService.handle(new ExistsVehicleByIdQuery(vehicleId));
    }

    @Override
    public Optional<Long> findVehicleIdByDeviceSerial(String deviceSerial) {
        return deviceAssignmentRepository.findVehicleIdByDeviceSerial(deviceSerial);
    }

    @Override
    public Optional<Long> findFleetIdByVehicleId(Long vehicleId) {
        return vehicleQueryService.handle(new GetVehicleByIdQuery(vehicleId))
                .map(v -> v.getFleetId());
    }

    @Override
    public Optional<String> findVehiclePlateById(Long vehicleId) {
        return vehicleQueryService.handle(new GetVehicleByIdQuery(vehicleId))
                .map(v -> v.getPlate());
    }
}