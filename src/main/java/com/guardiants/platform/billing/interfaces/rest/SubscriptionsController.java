package com.guardiants.platform.billing.interfaces.rest;

import com.guardiants.platform.billing.application.commandservices.SubscriptionCommandService;
import com.guardiants.platform.billing.application.queryservices.SubscriptionQueryService;
import com.guardiants.platform.billing.domain.model.queries.GetCurrentSubscriptionQuery;
import com.guardiants.platform.billing.interfaces.rest.resources.SelectPlanResource;
import com.guardiants.platform.billing.interfaces.rest.transform.ResponseEntityFromBillingQueryResultAssembler;
import com.guardiants.platform.billing.interfaces.rest.transform.SelectPlanCommandFromResourceAssembler;
import com.guardiants.platform.billing.interfaces.rest.transform.ResponseEntityFromSubscriptionCommandResultAssembler;
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
@RequestMapping(value = "/api/v1/billing/subscriptions", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Subscriptions", description = "Subscription and plan management")
public class SubscriptionsController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;
    private final MessageSource messageSource;

    public SubscriptionsController(SubscriptionCommandService subscriptionCommandService,
                                   SubscriptionQueryService subscriptionQueryService,
                                   MessageSource messageSource) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Select a plan", description = "Creates or updates the subscription to the given plan.")
    @PostMapping
    public ResponseEntity<?> selectPlan(@Valid @RequestBody SelectPlanResource resource) {
        log.debug("POST /api/v1/billing/subscriptions - ownerId={}, planId={}",
                resource.ownerId(), resource.planId());
        var command = SelectPlanCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = subscriptionCommandService.handle(command);
        return ResponseEntityFromSubscriptionCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get current subscription", description = "Returns the owner's active subscription.")
    @GetMapping("/current")
    public ResponseEntity<?> getCurrentSubscription(@RequestParam Long ownerId) {
        log.debug("GET /api/v1/billing/subscriptions/current?ownerId={}", ownerId);
        var sub = subscriptionQueryService.handle(new GetCurrentSubscriptionQuery(ownerId));
        return ResponseEntityFromBillingQueryResultAssembler.toResponseEntityFromSubscription(sub);
    }
}