package com.guardiants.platform.fleet.application.internal.commandservices;

import com.guardiants.platform.fleet.application.commandservices.FleetCommandFailure;
import com.guardiants.platform.fleet.application.commandservices.FleetCommandService;
import com.guardiants.platform.fleet.domain.model.aggregates.Fleet;
import com.guardiants.platform.fleet.domain.model.commands.CreateFleetCommand;
import com.guardiants.platform.fleet.domain.repositories.FleetRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class FleetCommandServiceImpl implements FleetCommandService {

    private final FleetRepository fleetRepository;

    public FleetCommandServiceImpl(FleetRepository fleetRepository) {
        this.fleetRepository = fleetRepository;
    }

    @Override
    public Result<Fleet, FleetCommandFailure> handle(CreateFleetCommand command) {
        var fleet = new Fleet(command);
        return Result.success(fleetRepository.save(fleet));
    }
}