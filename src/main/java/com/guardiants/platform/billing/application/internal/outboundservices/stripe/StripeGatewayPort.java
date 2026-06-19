// StripeGatewayPort.java
package com.guardiants.platform.billing.application.internal.outboundservices.stripe;
import com.guardiants.platform.billing.domain.model.valueobjects.PaymentStatus;

public interface StripeGatewayPort {
    String createCheckoutSession(Long subscriptionId, double priceUsd, String customerId);
    String createCustomer(Long ownerId, String email);
    PaymentStatus confirmPaymentIntent(String paymentIntentId);
    void cancelSubscription(String stripeSubscriptionId);
}