package com.guardiants.platform.billing.application.queryservices;

import com.guardiants.platform.billing.domain.model.aggregates.Payment;
import com.guardiants.platform.billing.domain.model.queries.GetPaymentHistoryQuery;

import java.util.List;

public interface PaymentQueryService {
    List<Payment> handle(GetPaymentHistoryQuery query);
}
