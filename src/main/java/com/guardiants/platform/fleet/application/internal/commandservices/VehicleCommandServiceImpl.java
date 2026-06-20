package com.guardiants.platform.fleet.application.internal.commandservices;

import com.guardiants.platform.fleet.application.commandservices.VehicleCommandFailure;
import com.guardiants.platform.fleet.application.commandservices.VehicleCommandService;
import com.guardiants.platform.fleet.domain.model.aggregates.Vehicle;
import com.guardiants.platform.fleet.domain.model.commands.DeactivateVehicleCommand;
import com.guardiants.platform.fleet.domain.model.commands.RegisterVehicleCommand;
import com.guardiants.platform.fleet.domain.model.commands.UpdateVehicleCommand;
import com.guardiants.platform.fleet.domain.model.events.VehicleRegisteredEvent;
import com.guardiants.platform.fleet.domain.model.events.VehicleUpdatedEvent;
import com.guardiants.platform.fleet.domain.repositories.DeviceAssignmentRepository;
import com.guardiants.platform.fleet.domain.repositories.VehicleRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class VehicleCommandServiceImpl implements VehicleCommandService {

    private final VehicleRepository vehicleRepository;
    private final DeviceAssignmentRepository deviceAssignmentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public VehicleCommandServiceImpl(VehicleRepository vehicleRepository,
                                     DeviceAssignmentRepository deviceAssignmentRepository,
                                     ApplicationEventPublisher eventPublisher) {
        this.vehicleRepository = vehicleRepository;
        this.deviceAssignmentRepository = deviceAssignmentRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<Vehicle, VehicleCommandFailure> handle(RegisterVehicleCommand command) {
        if (vehicleRepository.existsByPlate(command.plate())) {
            return Result.failure(new VehicleCommandFailure.PlateAlreadyExists());
        }
        var vehicle = new Vehicle(command);
        var saved = vehicleRepository.save(vehicle);
        eventPublisher.publishEvent(
                new VehicleRegisteredEvent(saved.getId(), saved.getFleetId(), saved.getPlate()));
        return Result.success(saved);
    }

    @Override
    public Result<Vehicle, VehicleCommandFailure> handle(UpdateVehicleCommand command) {
        return vehicleRepository.findById(command.vehicleId())
                .map(v -> {
                    v.updateInformation(command.model(), command.brand(), command.year());
                    var saved = vehicleRepository.save(v);
                    eventPublisher.publishEvent(
                            new VehicleUpdatedEvent(saved.getId(), saved.getPlate()));
                    return Result.<Vehicle, VehicleCommandFailure>success(saved);
                })
                .orElse(Result.failure(new VehicleCommandFailure.VehicleNotFound()));
    }

    @Override
    public Result<Vehicle, VehicleCommandFailure> handle(DeactivateVehicleCommand command) {
        return vehicleRepository.findById(command.vehicleId())
                .map(v -> {
                    v.deactivate();
                    return Result.<Vehicle, VehicleCommandFailure>success(
                            vehicleRepository.save(v));
                })
                .orElse(Result.failure(new VehicleCommandFailure.VehicleNotFound()));
    }
}
