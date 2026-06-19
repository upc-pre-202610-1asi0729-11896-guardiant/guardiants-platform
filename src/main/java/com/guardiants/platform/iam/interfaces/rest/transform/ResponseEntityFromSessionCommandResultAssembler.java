package com.guardiants.platform.iam.interfaces.rest.transform;

import com.guardiants.platform.iam.application.commandservices.SessionCommandFailure;
import com.guardiants.platform.iam.domain.model.aggregates.Session;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromSessionCommandResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Session, SessionCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                session -> ResponseEntity.ok(
                        SessionResourceFromEntityAssembler.toResourceFromEntity(session)),
                failure -> {
                    var detail = messageSource.getMessage(failure.messageKey(), null,
                            failure.messageKey(), LocaleContextHolder.getLocale());
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, detail));
                });
    }
}
