package com.guardiants.platform.billing.application.internal.commandservices;

import com.guardiants.platform.billing.application.commandservices.PaymentCommandFailure;
import com.guardiants.platform.billing.application.commandservices.PaymentCommandService;
import com.guardiants.platform.billing.application.internal.outboundservices.events.PaymentEventPublisher;
import com.guardiants.platform.billing.application.internal.outboundservices.stripe.StripeGatewayPort;
import com.guardiants.platform.billing.domain.model.aggregates.Payment;
import com.guardiants.platform.billing.domain.model.commands.ConfirmPaymentCommand;
import com.guardiants.platform.billing.domain.model.commands.ProcessPaymentCommand;
import com.guardiants.platform.billing.domain.model.commands.RecordPaymentFailureCommand;
import com.guardiants.platform.billing.domain.model.events.PaymentFailedEvent;
import com.guardiants.platform.billing.domain.model.events.PaymentSucceededEvent;
import com.guardiants.platform.billing.domain.model.valueobjects.PaymentStatus;
import com.guardiants.platform.billing.domain.repositories.PaymentRepository;
import com.guardiants.platform.billing.domain.repositories.SubscriptionRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final StripeGatewayPort stripeGatewayPort;
    private final PaymentEventPublisher paymentEventPublisher;

    public PaymentCommandServiceImpl(PaymentRepository paymentRepository,
                                     SubscriptionRepository subscriptionRepository,
                                     StripeGatewayPort stripeGatewayPort,
                                     PaymentEventPublisher paymentEventPublisher) {
        this.paymentRepository = paymentRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.stripeGatewayPort = stripeGatewayPort;
        this.paymentEventPublisher = paymentEventPublisher;
    }

    @Override
    public Result<Payment, PaymentCommandFailure> handle(ProcessPaymentCommand command) {
        var payment = new Payment(command);
        return Result.success(paymentRepository.save(payment));
    }

    @Override
    public Result<Payment, PaymentCommandFailure> handle(ConfirmPaymentCommand command) {
        var paymentOpt = paymentRepository.findByStripePaymentIntentId(
                command.stripePaymentIntentId());
        if (paymentOpt.isPresent()) {
            var payment = paymentOpt.get();
            var status = stripeGatewayPort.confirmPaymentIntent(command.stripePaymentIntentId());
            if (status == PaymentStatus.SUCCEEDED) {
                payment.markSucceeded();
            } else {
                payment.markFailed("Stripe confirmation failed");
                return Result.failure(new PaymentCommandFailure.Failed());
            }
            var saved = paymentRepository.save(payment);
            paymentEventPublisher.publishPaymentSucceeded(
                    new PaymentSucceededEvent(saved.getId(), saved.getSubscriptionId()));
            return Result.success(saved);
        }
        // first-time confirmation: create payment record then confirm
        if (subscriptionRepository.findById(command.subscriptionId()).isEmpty()) {
            return Result.failure(new PaymentCommandFailure.Failed());
        }
        var createCmd = new ProcessPaymentCommand(command.subscriptionId(), 0.0, "USD");
        var payment = new Payment(createCmd);
        payment.setStripePaymentIntentId(command.stripePaymentIntentId());
        payment.markSucceeded();
        return Result.success(paymentRepository.save(payment));
    }

    @Override
    public Result<Payment, PaymentCommandFailure> handle(RecordPaymentFailureCommand command) {
        var payment = paymentRepository.findByStripePaymentIntentId(
                command.stripePaymentIntentId())
                .orElseGet(() -> {
                    var p = new Payment(new ProcessPaymentCommand(
                            command.subscriptionId(), 0.0, "USD"));
                    p.setStripePaymentIntentId(command.stripePaymentIntentId());
                    return p;
                });
        payment.markFailed(command.failureReason());
        var saved = paymentRepository.save(payment);
        paymentEventPublisher.publishPaymentFailed(
                new PaymentFailedEvent(saved.getId(), saved.getSubscriptionId(),
                        command.failureReason()));
        return Result.success(saved);
    }
}
