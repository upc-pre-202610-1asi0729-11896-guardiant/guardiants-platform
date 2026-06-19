package com.guardiants.platform.iam.application.internal.outboundservices.events;

import com.guardiants.platform.iam.domain.model.events.AccountRegisteredEvent;
import com.guardiants.platform.iam.domain.model.events.EmailVerifiedEvent;

public interface IamEventPublisher {
    void publishAccountRegistered(AccountRegisteredEvent event);
    void publishEmailVerified(EmailVerifiedEvent event);
}