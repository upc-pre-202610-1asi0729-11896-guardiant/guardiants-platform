package com.guardiants.platform.fleet.interfaces.rest.transform;

import com.guardiants.platform.fleet.application.commandservices.VehicleLoanCommandFailure;
import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromVehicleLoanCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<VehicleLoan, VehicleLoanCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                loan -> new ResponseEntity<>(
                        VehicleLoanResourceFromEntityAssembler.toResourceFromEntity(loan),
                        HttpStatus.CREATED),
                failure -> {
                    var status = switch (failure) {
                        case VehicleLoanCommandFailure.LoanNotFound ignored -> HttpStatus.NOT_FOUND;
                        case VehicleLoanCommandFailure.NotAuthorizedApprover ignored -> HttpStatus.FORBIDDEN;
                        case VehicleLoanCommandFailure.VehicleNotAvailable ignored -> HttpStatus.CONFLICT;
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
