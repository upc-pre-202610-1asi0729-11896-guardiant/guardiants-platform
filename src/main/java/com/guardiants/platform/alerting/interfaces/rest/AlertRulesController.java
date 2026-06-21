package com.guardiants.platform.alerting.interfaces.rest;

import com.guardiants.platform.alerting.application.commandservices.AlertRuleCommandService;
import com.guardiants.platform.alerting.application.queryservices.AlertRuleQueryService;
import com.guardiants.platform.alerting.domain.model.queries.GetAlertRulesByOwnerIdQuery;
import com.guardiants.platform.alerting.interfaces.rest.resources.CreateAlertRuleResource;
import com.guardiants.platform.alerting.interfaces.rest.resources.UpdateAlertRuleResource;
import com.guardiants.platform.alerting.interfaces.rest.transform.CreateAlertRuleCommandFromResourceAssembler;
import com.guardiants.platform.alerting.interfaces.rest.transform.ResponseEntityFromAlertRuleQueryResultAssembler;
import com.guardiants.platform.alerting.interfaces.rest.transform.UpdateAlertRuleCommandFromResourceAssembler;
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
@RestController("alertingAlertRulesController")
@RequestMapping(value = "/api/v1/alert-rules", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Alert Rules", description = "Endpoints for fleet and personal alert rule management")
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

    @Operation(summary = "Update alert rule", description = "Enables, disables or modifies an existing alert rule.")
    @PutMapping("/{ruleId}")
    public ResponseEntity<?> updateAlertRule(@PathVariable Long ruleId,
                                             @Valid @RequestBody UpdateAlertRuleResource resource) {
        log.debug("PUT /api/v1/alert-rules/{}", ruleId);
        var command = UpdateAlertRuleCommandFromResourceAssembler.toCommandFromResource(ruleId, resource);
        var result = alertRuleCommandService.handle(command);
        return ResponseEntityFromAlertRuleCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get alert rules by owner", description = "Returns all alert rules configured by an owner.")
    @GetMapping
    public ResponseEntity<?> getAlertRules(@RequestParam Long ownerId) {
        log.debug("GET /api/v1/alert-rules?ownerId={}", ownerId);
        var rules = alertRuleQueryService.handle(new GetAlertRulesByOwnerIdQuery(ownerId));
        return ResponseEntityFromAlertRuleQueryResultAssembler.toResponseEntityFromList(rules);
    }
}
