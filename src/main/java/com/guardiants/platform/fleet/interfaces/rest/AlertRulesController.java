package com.guardiants.platform.fleet.interfaces.rest;

import com.guardiants.platform.fleet.application.commandservices.AlertRuleCommandService;
import com.guardiants.platform.fleet.interfaces.rest.resources.CreateAlertRuleResource;
import com.guardiants.platform.fleet.interfaces.rest.transform.CreateAlertRuleCommandFromResourceAssembler;
import com.guardiants.platform.fleet.interfaces.rest.transform.ResponseEntityFromAlertRuleCommandResultAssembler;
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
@RequestMapping(value = "/api/v1/fleet/alert-rules", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fleet Alert Rules", description = "Alert rule configuration for fleets")
public class AlertRulesController {

    private final AlertRuleCommandService alertRuleCommandService;
    private final MessageSource messageSource;

    public AlertRulesController(AlertRuleCommandService alertRuleCommandService,
                                MessageSource messageSource) {
        this.alertRuleCommandService = alertRuleCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Create alert rule",
            description = "Creates a geofence, speed limit or other alert rule for a fleet or vehicle.")
    @PostMapping
    public ResponseEntity<?> createAlertRule(
            @Valid @RequestBody CreateAlertRuleResource resource) {
        log.debug("POST /api/v1/fleet/alert-rules - fleetId={}, type={}",
                resource.fleetId(), resource.type());
        var command = CreateAlertRuleCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = alertRuleCommandService.handle(command);
        return ResponseEntityFromAlertRuleCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}
