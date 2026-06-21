package com.guardiants.platform.commands.application.queryservices;

import com.guardiants.platform.commands.domain.model.aggregates.Command;
import com.guardiants.platform.commands.domain.model.queries.GetCommandByIdQuery;
import com.guardiants.platform.commands.domain.model.queries.GetCommandsForVehicleQuery;
import com.guardiants.platform.commands.domain.model.queries.StreamCommandStatusQuery;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

public interface CommandQueryService {
    Optional<Command> handle(GetCommandByIdQuery query);
    List<Command> handle(GetCommandsForVehicleQuery query);
    Flux<Command> handle(StreamCommandStatusQuery query);
}
