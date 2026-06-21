package com.guardiants.platform.query.application.internal.projectors;

import com.guardiants.platform.query.domain.model.readmodels.RouteHistoryView;
import com.guardiants.platform.query.domain.model.readmodels.RouteSegmentView;
import com.guardiants.platform.query.domain.repositories.RouteHistoryViewRepository;
import com.guardiants.platform.telemetry.domain.model.events.TelemetryPointRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class RouteHistoryProjector {

    private final RouteHistoryViewRepository routeHistoryViewRepository;

    public RouteHistoryProjector(RouteHistoryViewRepository routeHistoryViewRepository) {
        this.routeHistoryViewRepository = routeHistoryViewRepository;
    }

    @EventListener
    public void on(TelemetryPointRegisteredEvent event) {
        Instant dayStart = event.occurredAt().truncatedTo(ChronoUnit.DAYS);
        Instant dayEnd = dayStart.plus(1, ChronoUnit.DAYS);

        var view = routeHistoryViewRepository
                .findByVehicleIdAndPeriod(event.vehicleId(), dayStart, dayEnd)
                .orElse(new RouteHistoryView(event.vehicleId(), dayStart, dayEnd));

        var segment = new RouteSegmentView();
        segment.setStartLat(event.location().lat());
        segment.setStartLng(event.location().lng());
        segment.setStartedAt(event.occurredAt());
        view.addSegment(segment);

        routeHistoryViewRepository.save(view);
        log.debug("RouteHistoryProjector: updated route history for vehicleId={}",
                event.vehicleId());
    }

    public void rebuild(Long vehicleId, Instant startDate, Instant endDate) {
        log.info("RouteHistoryProjector: rebuild requested for vehicleId={} [{} - {}]",
                vehicleId, startDate, endDate);
    }
}
