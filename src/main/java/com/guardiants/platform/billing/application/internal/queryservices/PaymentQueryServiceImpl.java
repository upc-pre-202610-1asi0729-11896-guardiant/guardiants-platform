package com.guardiants.platform.billing.application.internal.queryservices;

import com.guardiants.platform.billing.application.queryservices.PaymentQueryService;
import com.guardiants.platform.billing.domain.model.aggregates.Payment;
import com.guardiants.platform.billing.domain.model.queries.GetPaymentHistoryQuery;
import com.guardiants.platform.billing.domain.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> handle(GetPaymentHistoryQuery query) {
        return paymentRepository.findAllBySubscriptionId(query.subscriptionId());
    }
}
