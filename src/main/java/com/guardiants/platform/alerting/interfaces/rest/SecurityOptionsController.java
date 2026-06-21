package com.guardiants.platform.alerting.interfaces.rest;

import com.guardiants.platform.alerting.application.commandservices.SecurityOptionsCommandService;
import com.guardiants.platform.alerting.domain.model.commands.UpdateSecurityOptionsCommand;
import com.guardiants.platform.alerting.interfaces.rest.resources.UpdateSecurityOptionsResource;
import com.guardiants.platform.alerting.interfaces.rest.transform.ResponseEntityFromSecurityOptionsCommandResultAssembler;
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
@RequestMapping(value = "/api/v1/security-options", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Security Options", description = "Per-owner automatic security and defense options")
public class SecurityOptionsController {

    private final SecurityOptionsCommandService securityOptionsCommandService;
    private final MessageSource messageSource;

    public SecurityOptionsController(SecurityOptionsCommandService securityOptionsCommandService,
                                     MessageSource messageSource) {
        this.securityOptionsCommandService = securityOptionsCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Update security options",
            description = "Enables or disables suspicious movement detection, auto engine shutdown and auto safe mode.")
    @PutMapping("/{ownerId}")
    public ResponseEntity<?> updateSecurityOptions(
            @PathVariable Long ownerId,
            @Valid @RequestBody UpdateSecurityOptionsResource resource) {
        log.debug("PUT /api/v1/security-options/{}", ownerId);
        var command = new UpdateSecurityOptionsCommand(
                ownerId,
                resource.suspiciousMovementEnabled(),
                resource.autoEngineShutdownEnabled(),
                resource.autoSafeModeEnabled());
        var result = securityOptionsCommandService.handle(command);
        return ResponseEntityFromSecurityOptionsCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}
