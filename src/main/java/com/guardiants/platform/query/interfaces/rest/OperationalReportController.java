package com.guardiants.platform.query.interfaces.rest;

import com.guardiants.platform.query.application.queryservices.OperationalReportQueryService;
import com.guardiants.platform.query.domain.model.queries.GetOperationalReportQuery;
import com.guardiants.platform.query.interfaces.rest.transform.OperationalReportResourceFromViewAssembler;
import com.guardiants.platform.query.interfaces.rest.transform.ResponseEntityFromQueryResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/query/operational-reports", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Query — Operational Reports", description = "Read-model endpoints for fleet operational reports")
public class OperationalReportController {

    private final OperationalReportQueryService operationalReportQueryService;
    private final MessageSource messageSource;

    public OperationalReportController(OperationalReportQueryService operationalReportQueryService,
                                       MessageSource messageSource) {
        this.operationalReportQueryService = operationalReportQueryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Get operational report (US15)",
            description = "Returns the pre-computed operational report for a fleet in the given date range.")
    @GetMapping("/fleets/{fleetId}")
    public ResponseEntity<?> getOperationalReport(
            @PathVariable Long fleetId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        log.debug("GET /api/v1/query/operational-reports/fleets/{}?start={}&end={}",
                fleetId, startDate, endDate);
        var query = new GetOperationalReportQuery(
                fleetId, Instant.parse(startDate), Instant.parse(endDate));
        var view = operationalReportQueryService.handle(query);
        if (view.isEmpty()) {
            return ResponseEntityFromQueryResultAssembler.notFound(
                    messageSource, "query.error.noDataForPeriod", fleetId);
        }
        return ResponseEntity.ok(
                OperationalReportResourceFromViewAssembler.toResourceFromView(view.get()));
    }
}
