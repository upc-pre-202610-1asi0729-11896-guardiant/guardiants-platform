package com.guardiants.platform.telemetry.application.internal.queryservices;

import com.guardiants.platform.telemetry.application.queryservices.TelemetryQueryService;
import com.guardiants.platform.telemetry.domain.model.aggregates.RouteSegment;
import com.guardiants.platform.telemetry.domain.model.aggregates.TelemetryPoint;
import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import com.guardiants.platform.telemetry.domain.model.events.TelemetryPointRegisteredEvent;
import com.guardiants.platform.telemetry.domain.model.queries.GetLatestTelemetryPointQuery;
import com.guardiants.platform.telemetry.domain.model.queries.GetRouteHistoryQuery;
import com.guardiants.platform.telemetry.domain.model.queries.GetVehicleGeneralStatusQuery;
import com.guardiants.platform.telemetry.domain.model.queries.StreamLiveTelemetryQuery;
import com.guardiants.platform.telemetry.domain.repositories.RouteSegmentRepository;
import com.guardiants.platform.telemetry.domain.repositories.TelemetryPointRepository;
import com.guardiants.platform.telemetry.domain.repositories.VehicleGeneralStatusRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.List;
import java.util.Optional;

@Service
public class TelemetryQueryServiceImpl implements TelemetryQueryService {

    private final VehicleGeneralStatusRepository vehicleGeneralStatusRepository;
    private final TelemetryPointRepository telemetryPointRepository;
    private final RouteSegmentRepository routeSegmentRepository;

    private final Sinks.Many<TelemetryPoint> telemetrySink =
            Sinks.many().multicast().onBackpressureBuffer();

    public TelemetryQueryServiceImpl(VehicleGeneralStatusRepository vehicleGeneralStatusRepository,
                                     TelemetryPointRepository telemetryPointRepository,
                                     RouteSegmentRepository routeSegmentRepository) {
        this.vehicleGeneralStatusRepository = vehicleGeneralStatusRepository;
        this.telemetryPointRepository = telemetryPointRepository;
        this.routeSegmentRepository = routeSegmentRepository;
    }

    @Override
    public Optional<VehicleGeneralStatus> handle(GetVehicleGeneralStatusQuery query) {
        return vehicleGeneralStatusRepository.findByVehicleId(query.vehicleId());
    }

    @Override
    public Optional<TelemetryPoint> handle(GetLatestTelemetryPointQuery query) {
        return telemetryPointRepository.findLatestByVehicleId(query.vehicleId());
    }

    @Override
    public List<RouteSegment> handle(GetRouteHistoryQuery query) {
        return routeSegmentRepository.findAllByVehicleIdAndPeriod(
                query.vehicleId(), query.startDate(), query.endDate());
    }

    @Override
    public Flux<TelemetryPoint> handle(StreamLiveTelemetryQuery query) {
        return telemetrySink.asFlux()
                .filter(p -> p.getVehicleId().equals(query.vehicleId()));
    }

    /**
     * Pushes newly registered telemetry points to live SSE subscribers. Listening to the
     * domain event keeps the command side decoupled from the streaming concern.
     */
    @EventListener
    public void on(TelemetryPointRegisteredEvent event) {
        telemetryPointRepository.findById(event.telemetryPointId())
                .ifPresent(telemetrySink::tryEmitNext);
    }
}
