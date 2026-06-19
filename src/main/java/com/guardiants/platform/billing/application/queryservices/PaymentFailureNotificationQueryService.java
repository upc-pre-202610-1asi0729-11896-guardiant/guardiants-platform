package com.guardiants.platform.billing.application.queryservices;

import com.guardiants.platform.billing.domain.model.aggregates.PaymentFailureNotification;
import com.guardiants.platform.billing.domain.model.queries.GetPaymentFailureNotificationQuery;

import java.util.Optional;

public interface PaymentFailureNotificationQueryService {
    Optional<PaymentFailureNotification> handle(GetPaymentFailureNotificationQuery query);
}
