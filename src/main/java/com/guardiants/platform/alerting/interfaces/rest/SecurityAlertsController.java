package com.guardiants.platform.alerting.interfaces.rest;

import com.guardiants.platform.alerting.application.commandservices.SecurityAlertCommandService;
import com.guardiants.platform.alerting.application.queryservices.SecurityAlertQueryService;
import com.guardiants.platform.alerting.domain.model.commands.AcknowledgeAlertCommand;
import com.guardiants.platform.alerting.domain.model.commands.CloseAlertCommand;
import com.guardiants.platform.alerting.domain.model.queries.GetSecurityAlertByIdQuery;
import com.guardiants.platform.alerting.domain.model.queries.GetSecurityAlertsByOwnerIdQuery;
import com.guardiants.platform.alerting.domain.model.valueobjects.AlertFilterCategory;
import com.guardiants.platform.alerting.domain.model.valueobjects.AlertPeriod;
import com.guardiants.platform.alerting.interfaces.rest.resources.GenerateSecurityAlertResource;
import com.guardiants.platform.alerting.interfaces.rest.transform.GenerateSecurityAlertCommandFromResourceAssembler;
import com.guardiants.platform.alerting.interfaces.rest.transform.ResponseEntityFromSecurityAlertCommandResultAssembler;
import com.guardiants.platform.alerting.interfaces.rest.transform.ResponseEntityFromSecurityAlertQueryResultAssembler;
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
@RequestMapping(value = "/api/v1/security-alerts", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Security Alerts", description = "Endpoints for security alert generation and lifecycle")
public class SecurityAlertsController {

    private final SecurityAlertCommandService securityAlertCommandService;
    private final SecurityAlertQueryService securityAlertQueryService;
    private final MessageSource messageSource;

    public SecurityAlertsController(SecurityAlertCommandService securityAlertCommandService,
                                    SecurityAlertQueryService securityAlertQueryService,
                                    MessageSource messageSource) {
        this.securityAlertCommandService = securityAlertCommandService;
        this.securityAlertQueryService = securityAlertQueryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Generate security alert",
            description = "Creates a new security alert and sends push notification if preferences allow.")
    @PostMapping
    public ResponseEntity<?> generateSecurityAlert(
            @Valid @RequestBody GenerateSecurityAlertResource resource) {
        log.debug("POST /api/v1/security-alerts - vehicleId={}, type={}",
                resource.vehicleId(), resource.type());
        var command = GenerateSecurityAlertCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = securityAlertCommandService.handle(command);
        return ResponseEntityFromSecurityAlertCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Acknowledge alert", description = "Marks a security alert as read/acknowledged.")
    @PatchMapping("/{alertId}/acknowledge")
    public ResponseEntity<?> acknowledgeAlert(@PathVariable Long alertId) {
        log.debug("PATCH /api/v1/security-alerts/{}/acknowledge", alertId);
        var result = securityAlertCommandService.handle(new AcknowledgeAlertCommand(alertId));
        return ResponseEntityFromSecurityAlertCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Close alert", description = "Marks a security alert as closed.")
    @PatchMapping("/{alertId}/close")
    public ResponseEntity<?> closeAlert(@PathVariable Long alertId) {
        log.debug("PATCH /api/v1/security-alerts/{}/close", alertId);
        var result = securityAlertCommandService.handle(new CloseAlertCommand(alertId));
        return ResponseEntityFromSecurityAlertCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get security alerts by owner",
            description = "Returns security alerts filtered by category (ALL/UNREAD/URGENT) and period.")
    @GetMapping
    public ResponseEntity<?> getSecurityAlerts(
            @RequestParam Long ownerId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String period) {
        log.debug("GET /api/v1/security-alerts?ownerId={}&category={}&period={}", ownerId, category, period);
        var query = new GetSecurityAlertsByOwnerIdQuery(
                ownerId,
                category != null ? AlertFilterCategory.valueOf(category) : null,
                period != null ? AlertPeriod.valueOf(period) : null);
        var alerts = securityAlertQueryService.handle(query);
        return ResponseEntityFromSecurityAlertQueryResultAssembler.toResponseEntityFromList(alerts);
    }

    @Operation(summary = "Get security alert by ID", description = "Returns the detail of a single security alert.")
    @GetMapping("/{alertId}")
    public ResponseEntity<?> getSecurityAlertById(@PathVariable Long alertId) {
        log.debug("GET /api/v1/security-alerts/{}", alertId);
        var alert = securityAlertQueryService.handle(new GetSecurityAlertByIdQuery(alertId));
        if (alert.isEmpty()) {
            return ResponseEntityFromSecurityAlertQueryResultAssembler
                    .notFound(messageSource, "alerting.error.alertNotFound", alertId);
        }
        return ResponseEntityFromSecurityAlertQueryResultAssembler.toResponseEntityFromAlert(alert);
    }
}
