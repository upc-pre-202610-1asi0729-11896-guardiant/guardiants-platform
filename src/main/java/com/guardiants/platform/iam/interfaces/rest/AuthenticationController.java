package com.guardiants.platform.iam.interfaces.rest;

import com.guardiants.platform.iam.application.commandservices.AccountCommandService;
import com.guardiants.platform.iam.interfaces.rest.resources.RegisterAccountResource;
import com.guardiants.platform.iam.interfaces.rest.transform.RegisterAccountCommandFromResourceAssembler;
import com.guardiants.platform.iam.interfaces.rest.transform.ResponseEntityFromAccountCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/iam/auth", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Account registration and session management")
public class AuthenticationController {

    private final AccountCommandService accountCommandService;
    private final MessageSource messageSource;

    public AuthenticationController(AccountCommandService accountCommandService,
                                    MessageSource messageSource) {
        this.accountCommandService = accountCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Register account",
            description = "Creates a new account and sends a verification email.")
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterAccountResource resource) {
        log.debug("POST /api/v1/iam/auth/register - email={}", resource.email());
        var command = RegisterAccountCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var result = accountCommandService.handle(command);
        return ResponseEntityFromAccountCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}