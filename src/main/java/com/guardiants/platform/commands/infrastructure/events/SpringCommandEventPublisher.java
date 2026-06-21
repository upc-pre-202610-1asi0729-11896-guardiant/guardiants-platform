package com.guardiants.platform.commands.infrastructure.events;

import com.guardiants.platform.commands.application.internal.outboundservices.events.CommandEventPublisher;
import com.guardiants.platform.commands.domain.model.events.DeviceRestartedEvent;
import com.guardiants.platform.commands.domain.model.events.EngineBlockedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringCommandEventPublisher implements CommandEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public SpringCommandEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void publishEngineBlocked(EngineBlockedEvent event) {
        eventPublisher.publishEvent(event);
    }

    @Override
    public void publishDeviceRestarted(DeviceRestartedEvent event) {
        eventPublisher.publishEvent(event);
    }
}
