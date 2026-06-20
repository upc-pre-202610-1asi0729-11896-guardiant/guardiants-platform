package com.guardiants.platform.fleet.interfaces.rest.transform;

import com.guardiants.platform.fleet.application.commandservices.VehicleCommandFailure;
import com.guardiants.platform.fleet.domain.model.aggregates.Vehicle;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseEntityFromVehicleCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Vehicle, VehicleCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                vehicle -> new ResponseEntity<>(
                        VehicleResourceFromEntityAssembler.toResourceFromEntity(vehicle, Optional.empty()),
                        HttpStatus.CREATED),
                failure -> {
                    var status = switch (failure) {
                        case VehicleCommandFailure.VehicleNotFound ignored -> HttpStatus.NOT_FOUND;
                        case VehicleCommandFailure.DeviceAssignmentNotFound ignored -> HttpStatus.NOT_FOUND;
                        case VehicleCommandFailure.PlateAlreadyExists ignored -> HttpStatus.CONFLICT;
                        case VehicleCommandFailure.DeviceAlreadyAssigned ignored -> HttpStatus.CONFLICT;
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
