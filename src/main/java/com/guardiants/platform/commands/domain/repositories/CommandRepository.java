package com.guardiants.platform.commands.domain.repositories;

import com.guardiants.platform.commands.domain.model.aggregates.Command;
import java.util.List;
import java.util.Optional;

public interface CommandRepository {
    Optional<Command> findById(Long id);
    List<Command> findAllByVehicleId(Long vehicleId);
    Command save(Command command);
}
