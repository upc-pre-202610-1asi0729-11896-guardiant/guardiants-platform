package com.guardiants.platform.commands.interfaces.rest.transform;

import com.guardiants.platform.commands.domain.model.aggregates.Command;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class ResponseEntityFromCommandsQueryResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromCommand(Optional<Command> command) {
        return command
                .map(c -> ResponseEntity.ok(CommandResourceFromEntityAssembler.toResourceFromEntity(c)))
                .orElse(ResponseEntity.notFound().build());
    }

    public static ResponseEntity<?> toResponseEntityFromCommandList(List<Command> commands) {
        return ResponseEntity.ok(commands.stream()
                .map(CommandResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }

    public static ResponseEntity<ProblemDetail> notFound(MessageSource messageSource,
                                                         String messageKey, Object... args) {
        var detail = messageSource.getMessage(messageKey, args, messageKey,
                LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detail));
    }
}
