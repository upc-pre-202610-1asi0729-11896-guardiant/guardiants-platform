package com.guardiants.platform.billing.application.internal.eventhandlers;

import com.guardiants.platform.billing.application.commandservices.PaymentCommandService;
import com.guardiants.platform.billing.application.commandservices.PaymentFailureNotificationCommandService;
import com.guardiants.platform.billing.application.commandservices.SubscriptionCommandService;
import com.guardiants.platform.billing.application.queryservices.SubscriptionQueryService;
import com.guardiants.platform.billing.domain.model.commands.ConfirmPaymentCommand;
import com.guardiants.platform.billing.domain.model.commands.RecordPaymentFailureCommand;
import com.guardiants.platform.billing.domain.model.commands.RenewSubscriptionCommand;
import com.guardiants.platform.billing.domain.model.queries.GetSubscriptionByStripeIdQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
public class StripeWebhookService {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;
    private final PaymentCommandService paymentCommandService;
    private final PaymentFailureNotificationCommandService notificationCommandService;

    public StripeWebhookService(SubscriptionCommandService subscriptionCommandService,
                                 SubscriptionQueryService subscriptionQueryService,
                                 PaymentCommandService paymentCommandService,
                                 PaymentFailureNotificationCommandService notificationCommandService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
        this.paymentCommandService = paymentCommandService;
        this.notificationCommandService = notificationCommandService;
    }

    public void handlePaymentIntentSucceeded(String stripePaymentIntentId) {
        log.info("Stripe webhook: payment_intent.succeeded id={}", stripePaymentIntentId);
        paymentCommandService.handle(new ConfirmPaymentCommand(0L, stripePaymentIntentId));
    }

    public void handlePaymentIntentFailed(String stripePaymentIntentId, String failureReason) {
        log.warn("Stripe webhook: payment_intent.failed id={} reason={}",
                stripePaymentIntentId, failureReason);
        paymentCommandService.handle(
                new RecordPaymentFailureCommand(0L, stripePaymentIntentId, failureReason));
    }

    public void handleSubscriptionRenewed(String stripeSubscriptionId) {
        log.info("Stripe webhook: customer.subscription.updated id={}", stripeSubscriptionId);
        subscriptionQueryService.handle(new GetSubscriptionByStripeIdQuery(stripeSubscriptionId))
                .ifPresent(sub -> subscriptionCommandService.handle(
                        new RenewSubscriptionCommand(sub.getId(),
                                Instant.now().plus(30, ChronoUnit.DAYS))));
    }

    public void handleSubscriptionCanceled(String stripeSubscriptionId) {
        log.info("Stripe webhook: customer.subscription.deleted id={}", stripeSubscriptionId);
    }
}
