package com.guardiants.platform.billing.application.internal.commandservices;

import com.guardiants.platform.billing.application.commandservices.PaymentFailureNotificationCommandFailure;
import com.guardiants.platform.billing.application.commandservices.PaymentFailureNotificationCommandService;
import com.guardiants.platform.billing.application.internal.outboundservices.notification.BillingNotificationPort;
import com.guardiants.platform.billing.domain.model.aggregates.PaymentFailureNotification;
import com.guardiants.platform.billing.domain.model.commands.AcknowledgePaymentFailureCommand;
import com.guardiants.platform.billing.domain.model.commands.NotifyPaymentFailureCommand;
import com.guardiants.platform.billing.domain.repositories.PaymentFailureNotificationRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class PaymentFailureNotificationCommandServiceImpl
        implements PaymentFailureNotificationCommandService {

    private final PaymentFailureNotificationRepository repository;
    private final BillingNotificationPort notificationPort;

    public PaymentFailureNotificationCommandServiceImpl(
            PaymentFailureNotificationRepository repository,
            BillingNotificationPort notificationPort) {
        this.repository = repository;
        this.notificationPort = notificationPort;
    }

    @Override
    public Result<PaymentFailureNotification, PaymentFailureNotificationCommandFailure>
            handle(NotifyPaymentFailureCommand command) {
        var notification = new PaymentFailureNotification(command);
        var saved = repository.save(notification);
        notificationPort.sendPaymentFailureEmail(
                command.ownerId(), command.subscriptionId(), "Payment failed");
        return Result.success(saved);
    }

    @Override
    public Result<PaymentFailureNotification, PaymentFailureNotificationCommandFailure>
            handle(AcknowledgePaymentFailureCommand command) {
        return repository.findById(command.notificationId())
                .map(n -> {
                    n.acknowledge();
                    return Result.<PaymentFailureNotification,
                            PaymentFailureNotificationCommandFailure>success(repository.save(n));
                })
                .orElse(Result.failure(() -> "billing.error.subscriptionNotFound"));
    }
}
