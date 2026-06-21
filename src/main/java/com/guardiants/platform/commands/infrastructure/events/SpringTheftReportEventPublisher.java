package com.guardiants.platform.commands.infrastructure.events;

import com.guardiants.platform.commands.application.internal.outboundservices.events.TheftReportEventPublisher;
import com.guardiants.platform.commands.domain.model.events.TheftReportRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringTheftReportEventPublisher implements TheftReportEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public SpringTheftReportEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publishTheftReported(TheftReportRegisteredEvent event) {
        eventPublisher.publishEvent(event);
    }
}
