package com.guardiants.platform.query.interfaces.rest;

import com.guardiants.platform.query.application.queryservices.SearchQueryService;
import com.guardiants.platform.query.domain.model.readmodels.SearchEntityType;
import com.guardiants.platform.query.domain.model.queries.SearchQuery;
import com.guardiants.platform.query.interfaces.rest.transform.SearchResultResourceFromViewAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/query/search", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Query — Search", description = "Cross-entity search endpoint")
public class SearchController {

    private final SearchQueryService searchQueryService;

    public SearchController(SearchQueryService searchQueryService) {
        this.searchQueryService = searchQueryService;
    }

    @Operation(summary = "Search across entities",
            description = "Searches vehicles, routes, security events and operational reports.")
    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam String queryText,
            @RequestParam(required = false) List<String> entityTypes,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String vehicleStatus,
            @RequestParam(required = false) String eventType) {
        log.debug("GET /api/v1/query/search?q={}", queryText);
        var types = entityTypes != null
                ? entityTypes.stream().map(SearchEntityType::valueOf).toList()
                : null;
        var query = new SearchQuery(
                queryText, types,
                startDate != null ? Instant.parse(startDate) : null,
                endDate != null ? Instant.parse(endDate) : null,
                vehicleStatus, eventType, null, null);
        var results = searchQueryService.handle(query).stream()
                .map(SearchResultResourceFromViewAssembler::toResourceFromView)
                .toList();
        return ResponseEntity.ok(results);
    }
}
