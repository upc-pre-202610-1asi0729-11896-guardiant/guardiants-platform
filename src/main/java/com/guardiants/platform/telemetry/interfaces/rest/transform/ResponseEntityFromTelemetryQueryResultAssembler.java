package com.guardiants.platform.telemetry.interfaces.rest.transform;

import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import com.guardiants.platform.telemetry.interfaces.rest.resources.TelemetryPointResource;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public class ResponseEntityFromTelemetryQueryResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromVehicleGeneralStatus(
            Optional<VehicleGeneralStatus> status,
            TelemetryPointResource latestPoint) {
        return status
                .map(s -> ResponseEntity.ok(
                        VehicleGeneralStatusResourceFromEntityAssembler
                                .toResourceFromEntity(s, latestPoint)))
                .orElse(ResponseEntity.notFound().build());
    }

    public static ResponseEntity<ProblemDetail> notFound(MessageSource messageSource,
                                                         String messageKey, Object... args) {
        var detail = messageSource.getMessage(messageKey, args, messageKey,
                LocaleContextHolder.getLocale());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, detail));
    }
}
