package com.guardiants.platform.billing.interfaces.rest;

import com.guardiants.platform.billing.application.internal.eventhandlers.StripeWebhookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/billing/webhook/stripe", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Stripe Webhook", description = "Stripe event ingestion endpoint")
public class StripeWebhookController {

    private final StripeWebhookService stripeWebhookService;

    public StripeWebhookController(StripeWebhookService stripeWebhookService) {
        this.stripeWebhookService = stripeWebhookService;
    }

    @Operation(summary = "Handle Stripe webhook",
            description = "Receives Stripe events and dispatches to the appropriate handler.")
    @PostMapping
    public ResponseEntity<Void> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String signature) {
        // TODO: verify Stripe signature in production
        if (payload.contains("payment_intent.succeeded")) {
            stripeWebhookService.handlePaymentIntentSucceeded("pi_stub");
        } else if (payload.contains("payment_intent.payment_failed")) {
            stripeWebhookService.handlePaymentIntentFailed("pi_stub", "Insufficient funds");
        } else if (payload.contains("customer.subscription.updated")) {
            stripeWebhookService.handleSubscriptionRenewed("sub_stub");
        } else if (payload.contains("customer.subscription.deleted")) {
            stripeWebhookService.handleSubscriptionCanceled("sub_stub");
        }
        return ResponseEntity.ok().build();
    }
}
