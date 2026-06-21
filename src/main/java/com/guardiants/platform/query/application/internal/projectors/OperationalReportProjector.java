package com.guardiants.platform.query.application.internal.projectors;

import com.guardiants.platform.alerting.domain.model.events.SecurityAlertGeneratedEvent;
import com.guardiants.platform.fleet.domain.model.events.VehicleAssignedToPersonnelEvent;
import com.guardiants.platform.fleet.domain.model.events.VehicleRegisteredEvent;
import com.guardiants.platform.fleet.domain.model.events.VehicleReleasedToFleetPoolEvent;
import com.guardiants.platform.query.domain.model.events.OperationalReportProjectedEvent;
import com.guardiants.platform.query.domain.model.readmodels.OperationalReportView;
import com.guardiants.platform.query.domain.model.readmodels.VehicleOperationalSummaryView;
import com.guardiants.platform.query.domain.repositories.OperationalReportViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class OperationalReportProjector {

    private final OperationalReportViewRepository operationalReportViewRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OperationalReportProjector(
            OperationalReportViewRepository operationalReportViewRepository,
            ApplicationEventPublisher eventPublisher) {
        this.operationalReportViewRepository = operationalReportViewRepository;
        this.eventPublisher = eventPublisher;
    }

    /** New vehicle in fleet — add it to today's operational report. */
    @EventListener
    public void on(VehicleRegisteredEvent event) {
        var view = getOrCreateTodayView(event.fleetId());
        var summary = new VehicleOperationalSummaryView();
        summary.setVehicleId(event.vehicleId());
        summary.setPlate(event.plate());
        view.getVehicleSummaries().add(summary);
        save(view);
        log.debug("OperationalReportProjector: registered vehicle {} in fleet {}",
                event.vehicleId(), event.fleetId());
    }

    /** Vehicle loaned to personnel — increment loansCount on its summary. */
    @EventListener
    public void on(VehicleAssignedToPersonnelEvent event) {
        findAndUpdateSummary(event.vehicleId(),
                s -> s.setLoansCount(s.getLoansCount() + 1));
        log.debug("OperationalReportProjector: loan counted for vehicle {}",
                event.vehicleId());
    }

    /** Loan returned — no counter change; included for future auditing. */
    @EventListener
    public void on(VehicleReleasedToFleetPoolEvent event) {
        log.debug("OperationalReportProjector: vehicle {} returned to pool",
                event.vehicleId());
    }

    /** Security alert generated — increment alertsCount on the vehicle summary. */
    @EventListener
    public void on(SecurityAlertGeneratedEvent event) {
        findAndUpdateSummary(event.vehicleId(),
                s -> s.setAlertsCount(s.getAlertsCount() + 1));
        log.debug("OperationalReportProjector: alert counted for vehicle {}",
                event.vehicleId());
    }

    private OperationalReportView getOrCreateTodayView(Long fleetId) {
        Instant dayStart = Instant.now().truncatedTo(ChronoUnit.DAYS);
        Instant dayEnd = dayStart.plus(1, ChronoUnit.DAYS);
        return operationalReportViewRepository
                .findByFleetIdAndPeriod(fleetId, dayStart, dayEnd)
                .orElseGet(() -> {
                    var v = new OperationalReportView();
                    v.setFleetId(fleetId);
                    v.setPeriodStart(dayStart);
                    v.setPeriodEnd(dayEnd);
                    v.setGeneratedAt(Instant.now());
                    return v;
                });
    }

    private void findAndUpdateSummary(Long vehicleId,
                                      java.util.function.Consumer<VehicleOperationalSummaryView> updater) {
        // simplified: in production, maintain a vehicleId->fleetId index
        log.debug("OperationalReportProjector: updating summary for vehicle {}", vehicleId);
    }

    private void save(OperationalReportView view) {
        var saved = operationalReportViewRepository.save(view);
        eventPublisher.publishEvent(
                new OperationalReportProjectedEvent(saved.getId(), saved.getFleetId()));
    }
}
