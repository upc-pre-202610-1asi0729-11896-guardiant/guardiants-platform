package com.guardiants.platform.billing.interfaces.rest;

import com.guardiants.platform.billing.application.commandservices.PaymentFailureNotificationCommandService;
import com.guardiants.platform.billing.application.queryservices.PaymentFailureNotificationQueryService;
import com.guardiants.platform.billing.domain.model.commands.AcknowledgePaymentFailureCommand;
import com.guardiants.platform.billing.domain.model.queries.GetPaymentFailureNotificationQuery;
import com.guardiants.platform.billing.interfaces.rest.transform.PaymentFailureNotificationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/billing/payment-failure-notifications",
        produces = APPLICATION_JSON_VALUE)
@Tag(name = "Payment Failure Notifications")
public class PaymentFailureNotificationsController {

    private final PaymentFailureNotificationCommandService commandService;
    private final PaymentFailureNotificationQueryService queryService;
    private final MessageSource messageSource;

    public PaymentFailureNotificationsController(
            PaymentFailureNotificationCommandService commandService,
            PaymentFailureNotificationQueryService queryService,
            MessageSource messageSource) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Get payment failure notification")
    @GetMapping
    public ResponseEntity<?> getPaymentFailureNotifications(@RequestParam Long ownerId) {
        return queryService.handle(new GetPaymentFailureNotificationQuery(ownerId))
                .map(n -> ResponseEntity.ok(
                        PaymentFailureNotificationResourceFromEntityAssembler
                                .toResourceFromEntity(n)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Acknowledge payment failure notification")
    @PatchMapping("/{notificationId}/acknowledge")
    public ResponseEntity<?> acknowledgePaymentFailure(@PathVariable Long notificationId) {
        var result = commandService.handle(
                new AcknowledgePaymentFailureCommand(notificationId));
        return result.fold(
                n -> ResponseEntity.ok(
                        PaymentFailureNotificationResourceFromEntityAssembler
                                .toResourceFromEntity(n)),
                failure -> ResponseEntity.badRequest().build());
    }
}
