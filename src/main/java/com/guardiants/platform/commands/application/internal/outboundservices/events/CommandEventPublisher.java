package com.guardiants.platform.commands.application.internal.outboundservices.events;

import com.guardiants.platform.commands.domain.model.events.DeviceRestartedEvent;
import com.guardiants.platform.commands.domain.model.events.EngineBlockedEvent;

public interface CommandEventPublisher {
    void publishEngineBlocked(EngineBlockedEvent event);
    void publishDeviceRestarted(DeviceRestartedEvent event);
}
