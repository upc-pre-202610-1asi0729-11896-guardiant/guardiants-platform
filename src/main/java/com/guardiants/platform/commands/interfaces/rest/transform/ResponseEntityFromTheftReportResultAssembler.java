package com.guardiants.platform.commands.interfaces.rest.transform;

import com.guardiants.platform.commands.application.commandservices.TheftReportCommandFailure;
import com.guardiants.platform.commands.domain.model.aggregates.TheftReport;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

public class ResponseEntityFromTheftReportResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<TheftReport, TheftReportCommandFailure> result,
            MessageSource messageSource) {
        return result.fold(
                report -> new ResponseEntity<>(
                        TheftReportResourceFromEntityAssembler.toResourceFromEntity(report),
                        HttpStatus.CREATED),
                failure -> {
                    var status = failure instanceof TheftReportCommandFailure.TheftReportNotFound
                            ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
                    var detail = messageSource.getMessage(
                            failure.messageKey(), null, failure.messageKey(),
                            LocaleContextHolder.getLocale());
                    return ResponseEntity.status(status)
                            .body(ProblemDetail.forStatusAndDetail(status, detail));
                });
    }
}
