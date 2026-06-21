package com.guardiants.platform.query.application.internal.projectors;

import com.guardiants.platform.query.domain.model.readmodels.DrivingReportView;
import com.guardiants.platform.query.domain.model.readmodels.RiskPatternType;
import com.guardiants.platform.query.domain.model.readmodels.RiskPatternView;
import com.guardiants.platform.query.domain.repositories.DrivingReportViewRepository;
import com.guardiants.platform.telemetry.domain.model.events.TelemetryPointRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class DrivingReportProjector {

    private final DrivingReportViewRepository drivingReportViewRepository;

    public DrivingReportProjector(DrivingReportViewRepository drivingReportViewRepository) {
        this.drivingReportViewRepository = drivingReportViewRepository;
    }

    @EventListener
    public void on(TelemetryPointRegisteredEvent event) {
        Instant dayStart = event.occurredAt().truncatedTo(ChronoUnit.DAYS);
        Instant dayEnd = dayStart.plus(1, ChronoUnit.DAYS);

        var view = drivingReportViewRepository
                .findByVehicleIdAndPeriod(event.vehicleId(), dayStart, dayEnd)
                .orElseGet(() -> buildEmptyView(event.vehicleId(), dayStart, dayEnd));

        applyPoint(view, event);
        drivingReportViewRepository.save(view);
        log.debug("DrivingReportProjector: updated driving report for vehicleId={}",
                event.vehicleId());
    }

    public void rebuild(Long vehicleId, Instant startDate, Instant endDate) {
        log.info("DrivingReportProjector: rebuild requested for vehicleId={}", vehicleId);
    }

    private DrivingReportView buildEmptyView(Long vehicleId, Instant start, Instant end) {
        var view = new DrivingReportView();
        view.setVehicleId(vehicleId);
        view.setPeriodStart(start);
        view.setPeriodEnd(end);
        view.setDrivingScore(100.0);
        return view;
    }

    private void applyPoint(DrivingReportView view, TelemetryPointRegisteredEvent event) {
        double speed = event.vehicleStatus().speedKmh();
        int samples = view.getTotalDurationMinutes() + 1;
        view.setTotalDurationMinutes(samples);
        view.setAverageSpeedKmh(
                (view.getAverageSpeedKmh() * (samples - 1) + speed) / samples);
        if (speed > 120) {
            view.setHarshAccelerationEvents(view.getHarshAccelerationEvents() + 1);
            upsertRiskPattern(view, RiskPatternType.SPEEDING);
        }
        view.setDrivingScore(Math.max(0,
                100.0 - (view.getHarshBrakingEvents()
                        + view.getHarshAccelerationEvents()) * 5.0));
    }

    private void upsertRiskPattern(DrivingReportView view, RiskPatternType type) {
        view.getRiskPatterns().stream()
                .filter(p -> p.getType() == type)
                .findFirst()
                .ifPresentOrElse(p -> {
                    p.setOccurrences(p.getOccurrences() + 1);
                    p.setLastDetectedAt(Instant.now());
                }, () -> {
                    var pattern = new RiskPatternView();
                    pattern.setType(type);
                    pattern.setOccurrences(1);
                    pattern.setLastDetectedAt(Instant.now());
                    view.getRiskPatterns().add(pattern);
                });
    }
}
