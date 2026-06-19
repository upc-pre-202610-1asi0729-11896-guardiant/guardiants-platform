package com.guardiants.platform.billing.application.commandservices;

import com.guardiants.platform.billing.domain.model.aggregates.Subscription;
import com.guardiants.platform.billing.domain.model.commands.ActivateSubscriptionCommand;
import com.guardiants.platform.billing.domain.model.commands.SelectPlanCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface SubscriptionCommandService {
    Result<Subscription, SubscriptionCommandFailure> handle(SelectPlanCommand command);
    Result<Subscription, SubscriptionCommandFailure> handle(ActivateSubscriptionCommand command);
}