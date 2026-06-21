package com.guardiants.platform.query.interfaces.rest.transform;

import com.guardiants.platform.query.domain.model.readmodels.RouteHistoryView;
import com.guardiants.platform.query.interfaces.rest.resources.RouteHistoryResource;
import com.guardiants.platform.query.interfaces.rest.resources.RouteSegmentResource;

public class RouteHistoryResourceFromViewAssembler {

    public static RouteHistoryResource toResourceFromView(RouteHistoryView view) {
        var segments = view.getSegments().stream()
                .map(s -> new RouteSegmentResource(
                        s.getSegmentId(), s.getStartedAt(), s.getEndedAt(),
                        s.getStartLat(), s.getStartLng(),
                        s.getEndLat(), s.getEndLng(),
                        s.getDistanceKm(), s.getDurationMinutes()))
                .toList();
        return new RouteHistoryResource(
                view.getId(), view.getVehicleId(),
                view.getPeriodStart(), view.getPeriodEnd(),
                segments, view.getTotalDistanceKm(),
                view.getTotalDurationMinutes(), view.getTotalTripsCount());
    }
}
