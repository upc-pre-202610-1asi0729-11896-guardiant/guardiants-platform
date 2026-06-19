package com.guardiants.platform.billing.application.commandservices;

import com.guardiants.platform.billing.domain.model.aggregates.Subscription;
import com.guardiants.platform.billing.domain.model.commands.ActivateSubscriptionCommand;
import com.guardiants.platform.billing.domain.model.commands.SelectPlanCommand;
import com.guardiants.platform.billing.domain.model.commands.CancelSubscriptionCommand;
import com.guardiants.platform.billing.domain.model.commands.ExpireSubscriptionCommand;
import com.guardiants.platform.billing.domain.model.commands.RenewSubscriptionCommand;
import com.guardiants.platform.billing.domain.model.commands.SuspendSubscriptionCommand;
import com.guardiants.platform.billing.domain.model.commands.UpdatePlanCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface SubscriptionCommandService {
    Result<Subscription, SubscriptionCommandFailure> handle(SelectPlanCommand command);
    Result<Subscription, SubscriptionCommandFailure> handle(ActivateSubscriptionCommand command);
    Result<Subscription, SubscriptionCommandFailure> handle(SuspendSubscriptionCommand command);
    Result<Subscription, SubscriptionCommandFailure> handle(RenewSubscriptionCommand command);
    Result<Subscription, SubscriptionCommandFailure> handle(UpdatePlanCommand command);
    Result<Subscription, SubscriptionCommandFailure> handle(CancelSubscriptionCommand command);
    Result<Subscription, SubscriptionCommandFailure> handle(ExpireSubscriptionCommand command);
}