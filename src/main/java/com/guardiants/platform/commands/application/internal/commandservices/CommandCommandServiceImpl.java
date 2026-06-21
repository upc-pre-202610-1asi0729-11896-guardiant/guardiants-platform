package com.guardiants.platform.commands.application.internal.commandservices;

import com.guardiants.platform.commands.application.commandservices.CommandCommandService;
import com.guardiants.platform.commands.application.commandservices.CommandFailure;
import com.guardiants.platform.commands.application.internal.outboundservices.acl.FleetVehicleService;
import com.guardiants.platform.commands.application.internal.outboundservices.events.CommandEventPublisher;
import com.guardiants.platform.commands.application.internal.outboundservices.mqtt.CommandDispatchPort;
import com.guardiants.platform.commands.domain.model.aggregates.Command;
import com.guardiants.platform.commands.domain.model.commands.IssueEngineBlockCommand;
import com.guardiants.platform.commands.domain.model.commands.IssueDeviceRestartCommand;
import com.guardiants.platform.commands.domain.model.commands.IssueEngineUnblockCommand;
import com.guardiants.platform.commands.domain.model.events.DeviceRestartedEvent;
import com.guardiants.platform.commands.domain.model.events.EngineBlockedEvent;
import com.guardiants.platform.commands.domain.repositories.CommandRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class CommandCommandServiceImpl implements CommandCommandService {

    private final CommandRepository commandRepository;
    private final FleetVehicleService fleetVehicleService;
    private final CommandDispatchPort commandDispatchPort;
    private final CommandEventPublisher commandEventPublisher;

    public CommandCommandServiceImpl(CommandRepository commandRepository,
                                     FleetVehicleService fleetVehicleService,
                                     CommandDispatchPort commandDispatchPort,
                                     CommandEventPublisher commandEventPublisher) {
        this.commandRepository = commandRepository;
        this.fleetVehicleService = fleetVehicleService;
        this.commandDispatchPort = commandDispatchPort;
        this.commandEventPublisher = commandEventPublisher;
    }

    @Override
    public Result<Command, CommandFailure> handle(IssueEngineBlockCommand issueCommand) {
        if (!fleetVehicleService.existsVehicleById(issueCommand.vehicleId())) {
            return Result.failure(new CommandFailure.VehicleNotFound());
        }
        var command = new Command(issueCommand);
        var saved = commandRepository.save(command);

        // dispatch to device via MQTT
        commandDispatchPort.dispatch(saved.getId(), saved.getVehicleId(), saved.getType());
        saved.markDispatched();
        saved = commandRepository.save(saved);

        // publish cross-BC event: Telemetry subscribes to update isLocked on VehicleGeneralStatus
        commandEventPublisher.publishEngineBlocked(
                new EngineBlockedEvent(saved.getVehicleId(), saved.getId()));

        return Result.success(saved);
    }

    @Override
    public Result<Command, CommandFailure> handle(IssueEngineUnblockCommand issueCommand) {
        if (!fleetVehicleService.existsVehicleById(issueCommand.vehicleId())) {
            return Result.failure(new CommandFailure.VehicleNotFound());
        }
        var command = new Command(issueCommand);
        var saved = commandRepository.save(command);
        commandDispatchPort.dispatch(saved.getId(), saved.getVehicleId(), saved.getType());
        saved.markDispatched();
        return Result.success(commandRepository.save(saved));
    }

    @Override
    public Result<Command, CommandFailure> handle(IssueDeviceRestartCommand issueCommand) {
        if (!fleetVehicleService.existsVehicleById(issueCommand.vehicleId())) {
            return Result.failure(new CommandFailure.VehicleNotFound());
        }
        var command = new Command(issueCommand);
        var saved = commandRepository.save(command);
        commandDispatchPort.dispatch(saved.getId(), saved.getVehicleId(), saved.getType());
        saved.markDispatched();
        var completed = commandRepository.save(saved);
        commandEventPublisher.publishDeviceRestarted(
                new DeviceRestartedEvent(completed.getVehicleId(), completed.getId()));
        return Result.success(completed);
    }
}
