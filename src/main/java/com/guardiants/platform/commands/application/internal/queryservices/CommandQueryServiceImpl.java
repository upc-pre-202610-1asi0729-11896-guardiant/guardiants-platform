package com.guardiants.platform.commands.application.internal.queryservices;

import com.guardiants.platform.commands.application.queryservices.CommandQueryService;
import com.guardiants.platform.commands.domain.model.aggregates.Command;
import com.guardiants.platform.commands.domain.model.events.CommandAckReceivedEvent;
import com.guardiants.platform.commands.domain.model.queries.GetCommandByIdQuery;
import com.guardiants.platform.commands.domain.model.queries.GetCommandsForVehicleQuery;
import com.guardiants.platform.commands.domain.model.queries.StreamCommandStatusQuery;
import com.guardiants.platform.commands.domain.repositories.CommandRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Optional;

@Service
public class CommandQueryServiceImpl implements CommandQueryService {

    private final CommandRepository commandRepository;
    private final Sinks.Many<Command> commandSink =
            Sinks.many().multicast().onBackpressureBuffer();

    public CommandQueryServiceImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Override
    public Optional<Command> handle(GetCommandByIdQuery query) {
        return commandRepository.findById(query.commandId());
    }

    @Override
    public List<Command> handle(GetCommandsForVehicleQuery query) {
        return commandRepository.findAllByVehicleId(query.vehicleId());
    }

    @Override
    public Flux<Command> handle(StreamCommandStatusQuery query) {
        return commandSink.asFlux()
                .filter(c -> c.getId().equals(query.commandId()));
    }

    /** Pushes command status changes to SSE subscribers when a device ACK is processed. */
    @EventListener
    public void on(CommandAckReceivedEvent event) {
        commandRepository.findById(event.commandId()).ifPresent(commandSink::tryEmitNext);
    }
}
