package com.guardiants.platform.billing.application.commandservices;

import com.guardiants.platform.billing.domain.model.aggregates.PaymentFailureNotification;
import com.guardiants.platform.billing.domain.model.commands.AcknowledgePaymentFailureCommand;
import com.guardiants.platform.billing.domain.model.commands.NotifyPaymentFailureCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface PaymentFailureNotificationCommandService {
    Result<PaymentFailureNotification, PaymentFailureNotificationCommandFailure>
            handle(NotifyPaymentFailureCommand command);
    Result<PaymentFailureNotification, PaymentFailureNotificationCommandFailure>
            handle(AcknowledgePaymentFailureCommand command);
}
