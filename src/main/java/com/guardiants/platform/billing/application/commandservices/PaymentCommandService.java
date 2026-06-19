package com.guardiants.platform.billing.application.commandservices;

import com.guardiants.platform.billing.domain.model.aggregates.Payment;
import com.guardiants.platform.billing.domain.model.commands.ConfirmPaymentCommand;
import com.guardiants.platform.billing.domain.model.commands.ProcessPaymentCommand;
import com.guardiants.platform.billing.domain.model.commands.RecordPaymentFailureCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface PaymentCommandService {
    Result<Payment, PaymentCommandFailure> handle(ProcessPaymentCommand command);
    Result<Payment, PaymentCommandFailure> handle(ConfirmPaymentCommand command);
    Result<Payment, PaymentCommandFailure> handle(RecordPaymentFailureCommand command);
}
