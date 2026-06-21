package com.guardiants.platform.query.interfaces.rest;

import com.guardiants.platform.query.application.queryservices.DrivingReportQueryService;
import com.guardiants.platform.query.domain.model.queries.GetDrivingReportQuery;
import com.guardiants.platform.query.interfaces.rest.transform.DrivingReportResourceFromViewAssembler;
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
@RequestMapping(value = "/api/v1/query/driving-reports", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Query — Driving Reports", description = "Read-model endpoints for vehicle driving reports")
public class DrivingReportController {

    private final DrivingReportQueryService drivingReportQueryService;
    private final MessageSource messageSource;

    public DrivingReportController(DrivingReportQueryService drivingReportQueryService,
                                   MessageSource messageSource) {
        this.drivingReportQueryService = drivingReportQueryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Get driving report (US14 / TS45)",
            description = "Returns the pre-computed driving behavior report for a vehicle.")
    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<?> getDrivingReport(
            @PathVariable Long vehicleId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        log.debug("GET /api/v1/query/driving-reports/vehicles/{}?start={}&end={}",
                vehicleId, startDate, endDate);
        var query = new GetDrivingReportQuery(
                vehicleId, Instant.parse(startDate), Instant.parse(endDate));
        var view = drivingReportQueryService.handle(query);
        if (view.isEmpty()) {
            return ResponseEntityFromQueryResultAssembler.notFound(
                    messageSource, "query.error.noDataForPeriod", vehicleId);
        }
        return ResponseEntity.ok(
                DrivingReportResourceFromViewAssembler.toResourceFromView(view.get()));
    }
}
