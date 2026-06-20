package com.guardiants.platform.fleet.application.internal.eventhandlers;

import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.fleet.domain.model.events.VehicleAssignedToPersonnelEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Publishes {@link VehicleAssignedToPersonnelEvent} for Query BC projectors when a loan is
 * approved and assigned. Invoked programmatically from the vehicle loan command service.
 */
@Component
public class VehicleAssignedToPersonnelEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public VehicleAssignedToPersonnelEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(VehicleLoan loan) {
        eventPublisher.publishEvent(new VehicleAssignedToPersonnelEvent(
                loan.getId(), loan.getVehicleId(), loan.getRequestedByPersonnelId()));
    }
}
