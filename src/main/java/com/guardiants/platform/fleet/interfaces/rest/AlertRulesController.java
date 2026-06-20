package com.guardiants.platform.fleet.interfaces.rest;

import com.guardiants.platform.fleet.application.commandservices.AlertRuleCommandService;
import com.guardiants.platform.fleet.application.queryservices.AlertRuleQueryService;
import com.guardiants.platform.fleet.domain.model.commands.UpdateAlertRuleCommand;
import com.guardiants.platform.fleet.domain.model.queries.GetAllAlertRulesByFleetIdQuery;
import com.guardiants.platform.fleet.domain.model.valueobjects.Geofence;
import com.guardiants.platform.fleet.interfaces.rest.resources.AlertRuleResource;
import com.guardiants.platform.fleet.interfaces.rest.resources.CreateAlertRuleResource;
import com.guardiants.platform.fleet.interfaces.rest.resources.UpdateAlertRuleResource;
import com.guardiants.platform.fleet.interfaces.rest.transform.AlertRuleResourceFromEntityAssembler;
import com.guardiants.platform.fleet.interfaces.rest.transform.CreateAlertRuleCommandFromResourceAssembler;
import com.guardiants.platform.fleet.interfaces.rest.transform.ResponseEntityFromAlertRuleCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/fleet/alert-rules", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fleet Alert Rules", description = "Alert rule configuration for fleets")
public class AlertRulesController {

    private final AlertRuleCommandService alertRuleCommandService;
    private final AlertRuleQueryService alertRuleQueryService;
    private final MessageSource messageSource;

    public AlertRulesController(AlertRuleCommandService alertRuleCommandService,
                                AlertRuleQueryService alertRuleQueryService,
                                MessageSource messageSource) {
        this.alertRuleCommandService = alertRuleCommandService;
        this.alertRuleQueryService = alertRuleQueryService;
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

    @Operation(summary = "Update alert rule")
    @PutMapping("/{ruleId}")
    public ResponseEntity<?> updateAlertRule(@PathVariable Long ruleId,
                                             @Valid @RequestBody UpdateAlertRuleResource resource) {
        Geofence geofence = resource.geofence() != null
                ? new Geofence(resource.geofence().centerLat(),
                        resource.geofence().centerLng(), resource.geofence().radiusMeters())
                : null;
        var result = alertRuleCommandService.handle(
                new UpdateAlertRuleCommand(ruleId, resource.enabled(),
                        resource.speedThresholdKmh(), resource.prolongedStopThresholdMinutes(),
                        geofence));
        return ResponseEntityFromAlertRuleCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get all alert rules by fleet")
    @GetMapping
    public ResponseEntity<List<AlertRuleResource>> getAllAlertRulesByFleetId(
            @RequestParam Long fleetId) {
        return ResponseEntity.ok(alertRuleQueryService
                .handle(new GetAllAlertRulesByFleetIdQuery(fleetId))
                .stream().map(AlertRuleResourceFromEntityAssembler::toResourceFromEntity).toList());
    }
}
