package com.guardiants.platform.commands.interfaces.rest;

import com.guardiants.platform.commands.application.commandservices.TheftReportCommandService;
import com.guardiants.platform.commands.domain.model.commands.ResolveTheftReportCommand;
import com.guardiants.platform.commands.interfaces.rest.resources.ReportTheftResource;
import com.guardiants.platform.commands.interfaces.rest.transform.ReportTheftCommandFromResourceAssembler;
import com.guardiants.platform.commands.interfaces.rest.transform.ResponseEntityFromTheftReportResultAssembler;
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
@RequestMapping(value = "/api/v1/commands/theft-reports", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Theft Reports", description = "Vehicle theft reporting and emergency protocol")
public class TheftReportsController {

    private final TheftReportCommandService theftReportCommandService;
    private final MessageSource messageSource;

    public TheftReportsController(TheftReportCommandService theftReportCommandService,
                                 MessageSource messageSource) {
        this.theftReportCommandService = theftReportCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Report vehicle theft",
            description = "Registers a theft report and activates the emergency protocol "
                        + "(CRITICAL alert + auto engine block).")
    @PostMapping
    public ResponseEntity<?> reportTheft(@Valid @RequestBody ReportTheftResource resource) {
        log.debug("POST /api/v1/commands/theft-reports - vehicleId={}", resource.vehicleId());
        var command = ReportTheftCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = theftReportCommandService.handle(command);
        return ResponseEntityFromTheftReportResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Resolve theft report")
    @PatchMapping("/{reportId}/resolve")
    public ResponseEntity<?> resolveTheftReport(@PathVariable Long reportId) {
        var result = theftReportCommandService.handle(
                new ResolveTheftReportCommand(reportId));
        return ResponseEntityFromTheftReportResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}
