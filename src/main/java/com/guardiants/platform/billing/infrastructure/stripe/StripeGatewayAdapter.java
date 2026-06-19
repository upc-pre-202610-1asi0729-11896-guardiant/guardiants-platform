package com.guardiants.platform.billing.infrastructure.stripe;

import com.guardiants.platform.billing.application.internal.outboundservices.stripe.StripeGatewayPort;
import com.guardiants.platform.billing.domain.model.valueobjects.PaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StripeGatewayAdapter implements StripeGatewayPort {

    @Override
    public String createCheckoutSession(Long subscriptionId, double priceUsd, String customerId) {
        log.info("STRIPE STUB — createCheckoutSession sub={} price={}", subscriptionId, priceUsd);
        return "https://checkout.stripe.com/stub/" + subscriptionId;
    }

    @Override
    public String createCustomer(Long ownerId, String email) {
        log.info("STRIPE STUB — createCustomer ownerId={} email={}", ownerId, email);
        return "cus_stub_" + ownerId;
    }

    @Override
    public PaymentStatus confirmPaymentIntent(String paymentIntentId) {
        log.info("STRIPE STUB — confirmPaymentIntent id={}", paymentIntentId);
        return PaymentStatus.SUCCEEDED;
    }

    @Override
    public void cancelSubscription(String stripeSubscriptionId) {
        log.info("STRIPE STUB — cancelSubscription stripeId={}", stripeSubscriptionId);
    }
}