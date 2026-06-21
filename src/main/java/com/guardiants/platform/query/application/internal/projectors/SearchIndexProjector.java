package com.guardiants.platform.query.application.internal.projectors;

import com.guardiants.platform.alerting.domain.model.events.SecurityAlertGeneratedEvent;
import com.guardiants.platform.fleet.domain.model.events.VehicleRegisteredEvent;
import com.guardiants.platform.fleet.domain.model.events.VehicleUpdatedEvent;
import com.guardiants.platform.query.domain.model.events.OperationalReportProjectedEvent;
import com.guardiants.platform.query.domain.model.readmodels.SearchEntityType;
import com.guardiants.platform.query.domain.model.readmodels.SearchResultView;
import com.guardiants.platform.query.domain.repositories.*;
import com.guardiants.platform.telemetry.domain.model.events.TelemetryPointRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.time.Instant;

/**
 * Populates the {@code search_indices} table by listening to domain events from Fleet,
 * Telemetry and Alerting. Once active, the cross-entity search endpoint returns real results.
 */
@Slf4j
@Component
public class SearchIndexProjector {

    private final VehicleSearchIndexRepository vehicleSearchIndexRepository;
    private final RouteSearchIndexRepository routeSearchIndexRepository;
    private final SecurityEventSearchIndexRepository securityEventSearchIndexRepository;
    private final OperationalReportSearchIndexRepository operationalReportSearchIndexRepository;

    public SearchIndexProjector(
            VehicleSearchIndexRepository vehicleSearchIndexRepository,
            RouteSearchIndexRepository routeSearchIndexRepository,
            SecurityEventSearchIndexRepository securityEventSearchIndexRepository,
            OperationalReportSearchIndexRepository operationalReportSearchIndexRepository) {
        this.vehicleSearchIndexRepository = vehicleSearchIndexRepository;
        this.routeSearchIndexRepository = routeSearchIndexRepository;
        this.securityEventSearchIndexRepository = securityEventSearchIndexRepository;
        this.operationalReportSearchIndexRepository = operationalReportSearchIndexRepository;
    }

    /** Index vehicle when registered in Fleet BC. */
    @EventListener
    public void on(VehicleRegisteredEvent event) {
        var view = new SearchResultView();
        view.setEntityType(SearchEntityType.VEHICLE);
        view.setEntityId(event.vehicleId());
        view.setLabel(event.plate());
        view.setSubtitle("Fleet: " + event.fleetId());
        vehicleSearchIndexRepository.upsert(view);
        log.debug("SearchIndexProjector: indexed vehicle {}", event.vehicleId());
    }

    /** Re-index vehicle when plate is updated in Fleet BC. */
    @EventListener
    public void on(VehicleUpdatedEvent event) {
        var view = new SearchResultView();
        view.setEntityType(SearchEntityType.VEHICLE);
        view.setEntityId(event.vehicleId());
        view.setLabel(event.plate());
        vehicleSearchIndexRepository.upsert(view);
        log.debug("SearchIndexProjector: re-indexed vehicle {}", event.vehicleId());
    }

    /** Index route entry on each telemetry point from Telemetry BC. */
    @EventListener
    public void on(TelemetryPointRegisteredEvent event) {
        var view = new SearchResultView();
        view.setEntityType(SearchEntityType.ROUTE);
        view.setEntityId(event.vehicleId());
        view.setLabel("Route — vehicle " + event.vehicleId());
        view.setSubtitle(String.format("%.1f km/h · lat %.4f lng %.4f",
                event.vehicleStatus().speedKmh(), event.location().lat(), event.location().lng()));
        view.setLat(event.location().lat());
        view.setLng(event.location().lng());
        view.setOccurredAt(event.occurredAt());
        routeSearchIndexRepository.upsert(view);
    }

    /** Index security event when an alert is generated in Alerting BC. */
    @EventListener
    public void on(SecurityAlertGeneratedEvent event) {
        var view = new SearchResultView();
        view.setEntityType(SearchEntityType.SECURITY_EVENT);
        view.setEntityId(event.alertId());
        view.setLabel("Security Alert " + event.alertId());
        view.setSubtitle("Severity: " + event.severity() + " · vehicle " + event.vehicleId());
        view.setOccurredAt(Instant.now());
        securityEventSearchIndexRepository.upsert(view);
        log.debug("SearchIndexProjector: indexed security event {}", event.alertId());
    }

    /** Index operational report once built by OperationalReportProjector. */
    @EventListener
    public void on(OperationalReportProjectedEvent event) {
        var view = new SearchResultView();
        view.setEntityType(SearchEntityType.OPERATIONAL_REPORT);
        view.setEntityId(event.reportId());
        view.setLabel("Operational Report — Fleet " + event.fleetId());
        view.setOccurredAt(Instant.now());
        operationalReportSearchIndexRepository.upsert(view);
        log.debug("SearchIndexProjector: indexed operational report {}", event.reportId());
    }
}
