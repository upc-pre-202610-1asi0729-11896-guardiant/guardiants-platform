package com.guardiants.platform.billing.application.internal.queryservices;

import com.guardiants.platform.billing.application.queryservices.PaymentFailureNotificationQueryService;
import com.guardiants.platform.billing.domain.model.aggregates.PaymentFailureNotification;
import com.guardiants.platform.billing.domain.model.queries.GetPaymentFailureNotificationQuery;
import com.guardiants.platform.billing.domain.repositories.PaymentFailureNotificationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentFailureNotificationQueryServiceImpl
        implements PaymentFailureNotificationQueryService {

    private final PaymentFailureNotificationRepository repository;

    public PaymentFailureNotificationQueryServiceImpl(
            PaymentFailureNotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<PaymentFailureNotification> handle(GetPaymentFailureNotificationQuery query) {
        return repository.findByOwnerId(query.ownerId());
    }
}
