package com.guardiants.platform.billing.interfaces.rest;

import com.guardiants.platform.billing.application.queryservices.PlanQueryService;
import com.guardiants.platform.billing.domain.model.queries.GetAllPlansQuery;
import com.guardiants.platform.billing.interfaces.rest.resources.PlanResource;
import com.guardiants.platform.billing.interfaces.rest.transform.PlanResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/billing/plans", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Plans", description = "Available subscription plans")
public class PlansController {

    private final PlanQueryService planQueryService;

    public PlansController(PlanQueryService planQueryService) {
        this.planQueryService = planQueryService;
    }

    @Operation(summary = "Get all plans")
    @GetMapping
    public ResponseEntity<List<PlanResource>> getAllPlans() {
        var plans = planQueryService.handle(new GetAllPlansQuery());
        return ResponseEntity.ok(plans.stream()
                .map(PlanResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }
}
