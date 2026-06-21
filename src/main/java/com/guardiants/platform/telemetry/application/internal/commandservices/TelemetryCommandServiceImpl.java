package com.guardiants.platform.telemetry.application.internal.commandservices;

import com.guardiants.platform.telemetry.application.commandservices.TelemetryCommandFailure;
import com.guardiants.platform.telemetry.application.commandservices.TelemetryCommandService;
import com.guardiants.platform.telemetry.application.internal.outboundservices.acl.FleetVehicleService;
import com.guardiants.platform.telemetry.application.internal.outboundservices.events.TelemetryEventPublisher;
import com.guardiants.platform.telemetry.domain.model.aggregates.TelemetryPoint;
import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import com.guardiants.platform.telemetry.domain.model.commands.IngestTelemetryPointCommand;
import com.guardiants.platform.telemetry.domain.model.events.TelemetryPointRegisteredEvent;
import com.guardiants.platform.telemetry.domain.repositories.TelemetryPointRepository;
import com.guardiants.platform.telemetry.domain.repositories.VehicleGeneralStatusRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class TelemetryCommandServiceImpl implements TelemetryCommandService {

    private final TelemetryPointRepository telemetryPointRepository;
    private final VehicleGeneralStatusRepository vehicleGeneralStatusRepository;
    private final FleetVehicleService fleetVehicleService;
    private final TelemetryEventPublisher telemetryEventPublisher;

    public TelemetryCommandServiceImpl(
            TelemetryPointRepository telemetryPointRepository,
            VehicleGeneralStatusRepository vehicleGeneralStatusRepository,
            FleetVehicleService fleetVehicleService,
            TelemetryEventPublisher telemetryEventPublisher) {
        this.telemetryPointRepository = telemetryPointRepository;
        this.vehicleGeneralStatusRepository = vehicleGeneralStatusRepository;
        this.fleetVehicleService = fleetVehicleService;
        this.telemetryEventPublisher = telemetryEventPublisher;
    }

    @Override
    public Result<TelemetryPoint, TelemetryCommandFailure> handle(
            IngestTelemetryPointCommand command) {
        if (!fleetVehicleService.existsVehicleById(command.vehicleId())) {
            return Result.failure(new TelemetryCommandFailure.VehicleNotFound());
        }
        try {
            var point = new TelemetryPoint(command);
            var saved = telemetryPointRepository.save(point);

            // upsert vehicle general status
            var status = vehicleGeneralStatusRepository
                    .findByVehicleId(command.vehicleId())
                    .orElse(new VehicleGeneralStatus(command.vehicleId()));
            status.applyTelemetryPoint(saved);
            vehicleGeneralStatusRepository.save(status);

            telemetryEventPublisher.publishPointRegistered(new TelemetryPointRegisteredEvent(
                    saved.getId(), saved.getVehicleId(), saved.getLocation(),
                    saved.getVehicleStatus(), saved.getConnectivity().getValue(),
                    saved.getTimestamp()));

            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(new TelemetryCommandFailure.InvalidPayload());
        }
    }
}
