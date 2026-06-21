package com.guardiants.platform.alerting.interfaces.rest;

import com.guardiants.platform.alerting.application.commandservices.AlertRuleCommandService;
import com.guardiants.platform.alerting.interfaces.rest.resources.CreateAlertRuleResource;
import com.guardiants.platform.alerting.interfaces.rest.transform.CreateAlertRuleCommandFromResourceAssembler;
import com.guardiants.platform.alerting.interfaces.rest.transform.ResponseEntityFromAlertRuleCommandResultAssembler;
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
@RequestMapping(value = "/api/v1/alert-rules", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Alert Rules", description = "Endpoints for fleet and personal alert rule management")
public class AlertRulesController {

    private final AlertRuleCommandService alertRuleCommandService;
    private final MessageSource messageSource;

    public AlertRulesController(AlertRuleCommandService alertRuleCommandService,
                                MessageSource messageSource) {
        this.alertRuleCommandService = alertRuleCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Configure geofence or alert rule",
            description = "Creates a new alert rule (geofence, speed limit, GPS jamming or prolonged stop).")
    @PostMapping
    public ResponseEntity<?> createAlertRule(@Valid @RequestBody CreateAlertRuleResource resource) {
        log.debug("POST /api/v1/alert-rules - ownerId={}, type={}", resource.ownerId(), resource.type());
        var command = CreateAlertRuleCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = alertRuleCommandService.handle(command);
        return ResponseEntityFromAlertRuleCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}
