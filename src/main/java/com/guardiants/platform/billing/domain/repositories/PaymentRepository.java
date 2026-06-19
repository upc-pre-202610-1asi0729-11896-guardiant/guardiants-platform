package com.guardiants.platform.billing.domain.repositories;

import com.guardiants.platform.billing.domain.model.aggregates.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Optional<Payment> findById(Long id);
    List<Payment> findAllBySubscriptionId(Long subscriptionId);
    Optional<Payment> findByStripePaymentIntentId(String stripePaymentIntentId);
    Payment save(Payment payment);
}