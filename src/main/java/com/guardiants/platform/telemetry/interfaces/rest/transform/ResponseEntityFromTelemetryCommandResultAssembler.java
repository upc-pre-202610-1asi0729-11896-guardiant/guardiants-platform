package com.guardiants.platform.telemetry.interfaces.rest.transform;

import com.guardiants.platform.telemetry.application.commandservices.TelemetryCommandFailure;
import com.guardiants.platform.telemetry.domain.model.aggregates.TelemetryPoint;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromTelemetryCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<TelemetryPoint, TelemetryCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                point -> new ResponseEntity<>(
                        TelemetryPointResourceFromEntityAssembler.toResourceFromEntity(point),
                        HttpStatus.CREATED),
                failure -> {
                    var isNotFound = failure instanceof TelemetryCommandFailure.VehicleNotFound;
                    var status = isNotFound ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
                    var detail = messageSource.getMessage(
                            failure.messageKey(), null, failure.messageKey(),
                            LocaleContextHolder.getLocale());
                    return ResponseEntity.status(status)
                            .body(ProblemDetail.forStatusAndDetail(status, detail));
                });
    }
}
