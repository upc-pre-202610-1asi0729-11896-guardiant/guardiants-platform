package com.guardiants.platform.query.interfaces.rest;

import com.guardiants.platform.query.application.queryservices.RouteHistoryQueryService;
import com.guardiants.platform.query.domain.model.queries.GetRouteHistoryQuery;
import com.guardiants.platform.query.interfaces.rest.transform.ResponseEntityFromQueryResultAssembler;
import com.guardiants.platform.query.interfaces.rest.transform.RouteHistoryResourceFromViewAssembler;
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
@RequestMapping(value = "/api/v1/query/route-history", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Query — Route History", description = "Read-model endpoints for vehicle route history")
public class RouteHistoryController {

    private final RouteHistoryQueryService routeHistoryQueryService;
    private final MessageSource messageSource;

    public RouteHistoryController(RouteHistoryQueryService routeHistoryQueryService,
                                  MessageSource messageSource) {
        this.routeHistoryQueryService = routeHistoryQueryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Get route history (US03)",
            description = "Returns the pre-computed route history for a vehicle in the given date range.")
    @GetMapping("/vehicles/{vehicleId}")
    public ResponseEntity<?> getRouteHistory(
            @PathVariable Long vehicleId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        log.debug("GET /api/v1/query/route-history/vehicles/{}?start={}&end={}",
                vehicleId, startDate, endDate);
        var query = new GetRouteHistoryQuery(
                vehicleId, Instant.parse(startDate), Instant.parse(endDate));
        var view = routeHistoryQueryService.handle(query);
        if (view.isEmpty()) {
            return ResponseEntityFromQueryResultAssembler.notFound(
                    messageSource, "query.error.noDataForPeriod", vehicleId);
        }
        return ResponseEntity.ok(
                RouteHistoryResourceFromViewAssembler.toResourceFromView(view.get()));
    }
}
