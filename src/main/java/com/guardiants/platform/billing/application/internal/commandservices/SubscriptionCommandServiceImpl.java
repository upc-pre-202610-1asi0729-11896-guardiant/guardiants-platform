package com.guardiants.platform.billing.application.internal.commandservices;

import com.guardiants.platform.billing.application.commandservices.SubscriptionCommandFailure;
import com.guardiants.platform.billing.application.commandservices.SubscriptionCommandService;
import com.guardiants.platform.billing.application.internal.outboundservices.events.SubscriptionEventPublisher;
import com.guardiants.platform.billing.application.internal.outboundservices.stripe.StripeGatewayPort;
import com.guardiants.platform.billing.domain.model.aggregates.Subscription;
import com.guardiants.platform.billing.domain.model.commands.ActivateSubscriptionCommand;
import com.guardiants.platform.billing.domain.model.commands.SelectPlanCommand;
import com.guardiants.platform.billing.domain.model.events.SubscriptionActivatedEvent;
import com.guardiants.platform.billing.domain.repositories.PlanRepository;
import com.guardiants.platform.billing.domain.repositories.SubscriptionRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionCommandServiceImpl implements SubscriptionCommandService {

    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final StripeGatewayPort stripeGatewayPort;
    private final SubscriptionEventPublisher subscriptionEventPublisher;

    public SubscriptionCommandServiceImpl(SubscriptionRepository subscriptionRepository,
                                          PlanRepository planRepository,
                                          StripeGatewayPort stripeGatewayPort,
                                          SubscriptionEventPublisher subscriptionEventPublisher) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
        this.stripeGatewayPort = stripeGatewayPort;
        this.subscriptionEventPublisher = subscriptionEventPublisher;
    }

    @Override
    public Result<Subscription, SubscriptionCommandFailure> handle(SelectPlanCommand command) {
        if (planRepository.findById(command.planId()).isEmpty()) {
            return Result.failure(new SubscriptionCommandFailure.PlanNotFound());
        }
        // idempotent: if owner already has a subscription, update the plan
        var existing = subscriptionRepository.findByOwnerId(command.ownerId());
        if (existing.isPresent()) {
            var sub = existing.get();
            sub.updatePlan(command.planId());
            return Result.success(subscriptionRepository.save(sub));
        }
        var subscription = new Subscription(command);
        return Result.success(subscriptionRepository.save(subscription));
    }

    @Override
    public Result<Subscription, SubscriptionCommandFailure> handle(
            ActivateSubscriptionCommand command) {
        return subscriptionRepository.findById(command.subscriptionId())
                .map(sub -> {
                    sub.activate(command.stripeSubscriptionId(),
                            command.periodStart(), command.periodEnd());
                    var saved = subscriptionRepository.save(sub);
                    subscriptionEventPublisher.publishSubscriptionActivated(
                            new SubscriptionActivatedEvent(saved.getId(),
                                    saved.getOwnerId(), saved.getPlanId()));
                    return Result.<Subscription, SubscriptionCommandFailure>success(saved);
                })
                .orElse(Result.failure(new SubscriptionCommandFailure.NotFound()));
    }
}