package com.guardiants.platform.commands.application.internal.outboundservices.events;

import com.guardiants.platform.commands.domain.model.events.TheftReportRegisteredEvent;

public interface TheftReportEventPublisher {
    void publishTheftReported(TheftReportRegisteredEvent event);
}
