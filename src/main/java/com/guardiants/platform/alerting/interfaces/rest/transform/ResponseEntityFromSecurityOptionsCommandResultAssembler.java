package com.guardiants.platform.alerting.interfaces.rest.transform;

import com.guardiants.platform.alerting.application.commandservices.SecurityOptionsCommandFailure;
import com.guardiants.platform.alerting.domain.model.entities.SecurityOptions;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromSecurityOptionsCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<SecurityOptions, SecurityOptionsCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                options -> ResponseEntity.ok(
                        SecurityOptionsResourceFromEntityAssembler.toResourceFromEntity(options)),
                failure -> {
                    var detail = messageSource.getMessage(
                            failure.messageKey(), null, failure.messageKey(),
                            LocaleContextHolder.getLocale());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail));
                });
    }
}
