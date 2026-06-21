package com.guardiants.platform.query.interfaces.rest;

import com.guardiants.platform.query.application.commandservices.ReportExportCommandService;
import com.guardiants.platform.query.domain.model.commands.ExportReportCommand;
import com.guardiants.platform.query.domain.model.readmodels.ExportFormat;
import com.guardiants.platform.query.interfaces.rest.resources.ExportReportResource;
import com.guardiants.platform.query.interfaces.rest.transform.ReportExportResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/query/exports", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Query — Exports", description = "Report export to PDF/CSV")
public class ReportExportController {

    private final ReportExportCommandService reportExportCommandService;
    private final MessageSource messageSource;

    public ReportExportController(ReportExportCommandService reportExportCommandService,
                                  MessageSource messageSource) {
        this.reportExportCommandService = reportExportCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Export report to PDF or CSV",
            description = "Generates a downloadable file from an existing report view.")
    @PostMapping
    public ResponseEntity<?> exportReport(@Valid @RequestBody ExportReportResource resource) {
        log.debug("POST /api/v1/query/exports - sourceReportId={}, format={}",
                resource.sourceReportId(), resource.format());
        var command = new ExportReportCommand(
                resource.sourceReportId(), ExportFormat.valueOf(resource.format()));
        var result = reportExportCommandService.handle(command);
        return result.fold(
                export -> ResponseEntity.ok(
                        ReportExportResourceFromEntityAssembler.toResourceFromEntity(export)),
                failure -> {
                    var detail = messageSource.getMessage(failure.messageKey(), null,
                            failure.messageKey(), LocaleContextHolder.getLocale());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, detail));
                });
    }
}
