package com.guardiants.platform.billing.application.internal.eventhandlers;

import com.guardiants.platform.billing.application.commandservices.SubscriptionCommandService;
import com.guardiants.platform.billing.domain.model.commands.SelectPlanCommand;
import com.guardiants.platform.iam.domain.model.events.AccountRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Provisions a free plan subscription automatically when a new account is registered in IAM.
 * Listens to AccountRegisteredEvent — the cross-BC integration event from iam.domain.model.events.
 */
@Slf4j
@Component
public class IamAccountRegisteredEventHandler {

    private static final Long FREE_PLAN_ID = 1L;

    private final SubscriptionCommandService subscriptionCommandService;

    public IamAccountRegisteredEventHandler(SubscriptionCommandService subscriptionCommandService) {
        this.subscriptionCommandService = subscriptionCommandService;
    }

    @EventListener
    public void on(AccountRegisteredEvent event) {
        log.debug("IamAccountRegisteredEventHandler: provisioning free plan for ownerId={}",
                event.ownerId());
        var command = new SelectPlanCommand(event.ownerId(), FREE_PLAN_ID);
        var result = subscriptionCommandService.handle(command);
        if (result.isFailure()) {
            log.warn("Failed to provision free plan for ownerId={}: {}",
                    event.ownerId(), result.getOrElse(null));
        }
    }
}