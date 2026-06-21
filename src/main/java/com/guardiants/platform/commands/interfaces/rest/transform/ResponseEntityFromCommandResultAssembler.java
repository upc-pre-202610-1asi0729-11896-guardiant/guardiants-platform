package com.guardiants.platform.commands.interfaces.rest.transform;

import com.guardiants.platform.commands.application.commandservices.CommandFailure;
import com.guardiants.platform.commands.domain.model.aggregates.Command;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Command, CommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                command -> new ResponseEntity<>(
                        CommandResourceFromEntityAssembler.toResourceFromEntity(command),
                        HttpStatus.CREATED),
                failure -> {
                    var status = switch (failure) {
                        case CommandFailure.VehicleNotFound ignored -> HttpStatus.NOT_FOUND;
                        case CommandFailure.DeviceUnreachable ignored -> HttpStatus.SERVICE_UNAVAILABLE;
                        default -> HttpStatus.BAD_REQUEST;
                    };
                    var detail = messageSource.getMessage(
                            failure.messageKey(), null, failure.messageKey(),
                            LocaleContextHolder.getLocale());
                    return ResponseEntity.status(status)
                            .body(ProblemDetail.forStatusAndDetail(status, detail));
                });
    }
}
