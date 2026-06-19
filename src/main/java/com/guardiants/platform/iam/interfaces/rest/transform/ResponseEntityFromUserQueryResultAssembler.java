package com.guardiants.platform.iam.interfaces.rest.transform;

import com.guardiants.platform.iam.domain.model.aggregates.User;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public class ResponseEntityFromUserQueryResultAssembler {

    public static ResponseEntity<?> toResponseEntityFromUser(Optional<User> user) {
        return user
                .map(u -> ResponseEntity.ok(
                        UserResourceFromEntityAssembler.toResourceFromEntity(u)))
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