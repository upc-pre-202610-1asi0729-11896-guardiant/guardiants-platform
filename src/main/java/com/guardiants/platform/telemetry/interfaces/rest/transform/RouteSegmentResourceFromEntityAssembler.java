package com.guardiants.platform.telemetry.interfaces.rest.transform;

import com.guardiants.platform.telemetry.domain.model.aggregates.RouteSegment;
import com.guardiants.platform.telemetry.interfaces.rest.resources.RouteSegmentResource;
import java.util.List;

public class RouteSegmentResourceFromEntityAssembler {

    public static RouteSegmentResource toResourceFromEntity(RouteSegment segment) {
        return new RouteSegmentResource(
                segment.getId(),
                segment.getVehicleId(),
                segment.getStartedAt(),
                segment.getEndedAt(),
                segment.getDistanceKm(),
                List.of()); // points populated from join table if needed
    }
}
