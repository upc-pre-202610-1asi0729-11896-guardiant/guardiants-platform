package com.guardiants.platform.query.interfaces.rest.transform;

import com.guardiants.platform.query.domain.model.readmodels.DrivingReportView;
import com.guardiants.platform.query.interfaces.rest.resources.DrivingReportResource;
import com.guardiants.platform.query.interfaces.rest.resources.RiskPatternResource;

public class DrivingReportResourceFromViewAssembler {

    public static DrivingReportResource toResourceFromView(DrivingReportView view) {
        var patterns = view.getRiskPatterns().stream()
                .map(p -> new RiskPatternResource(
                        p.getType() != null ? p.getType().name() : null,
                        p.getOccurrences(), p.getLastDetectedAt()))
                .toList();
        return new DrivingReportResource(
                view.getId(), view.getVehicleId(),
                view.getPeriodStart(), view.getPeriodEnd(),
                view.getTotalDistanceKm(), view.getTotalDurationMinutes(),
                view.getAverageSpeedKmh(), view.getDrivingScore(),
                view.getHarshBrakingEvents(), view.getHarshAccelerationEvents(), patterns);
    }
}
