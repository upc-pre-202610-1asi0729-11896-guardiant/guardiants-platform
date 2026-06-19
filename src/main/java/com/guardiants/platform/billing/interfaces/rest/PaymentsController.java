package com.guardiants.platform.billing.interfaces.rest;

import com.guardiants.platform.billing.application.commandservices.PaymentCommandService;
import com.guardiants.platform.billing.application.queryservices.PaymentQueryService;
import com.guardiants.platform.billing.domain.model.queries.GetPaymentHistoryQuery;
import com.guardiants.platform.billing.interfaces.rest.resources.ConfirmPaymentResource;
import com.guardiants.platform.billing.interfaces.rest.resources.PaymentResource;
import com.guardiants.platform.billing.interfaces.rest.transform.ConfirmPaymentCommandFromResourceAssembler;
import com.guardiants.platform.billing.interfaces.rest.transform.PaymentResourceFromEntityAssembler;
import com.guardiants.platform.billing.interfaces.rest.transform.ResponseEntityFromPaymentCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/billing/payments", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Payments", description = "Payment confirmation and history")
public class PaymentsController {

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;
    private final MessageSource messageSource;

    public PaymentsController(PaymentCommandService paymentCommandService,
                               PaymentQueryService paymentQueryService,
                               MessageSource messageSource) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Confirm payment")
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPayment(@Valid @RequestBody ConfirmPaymentResource resource) {
        var command = ConfirmPaymentCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = paymentCommandService.handle(command);
        return ResponseEntityFromPaymentCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Get payment history")
    @GetMapping
    public ResponseEntity<List<PaymentResource>> getPaymentHistory(
            @RequestParam Long subscriptionId) {
        var payments = paymentQueryService.handle(new GetPaymentHistoryQuery(subscriptionId));
        return ResponseEntity.ok(payments.stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }
}
