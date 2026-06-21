package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.telemetry.domain.model.aggregates.RouteSegment;
import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.entities.RouteSegmentPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class RouteSegmentEntityAssembler {

    public RouteSegmentPersistenceEntity toPersistenceEntityFromDomain(RouteSegment segment) {
        var entity = new RouteSegmentPersistenceEntity();
        entity.setId(segment.getId());
        entity.setVehicleId(segment.getVehicleId());
        entity.setStartedAt(segment.getStartedAt());
        entity.setEndedAt(segment.getEndedAt());
        entity.setDistanceKm(segment.getDistanceKm());
        return entity;
    }

    public RouteSegment toDomainFromPersistenceEntity(RouteSegmentPersistenceEntity entity) {
        var segment = new RouteSegment(
                entity.getVehicleId(), entity.getStartedAt(),
                entity.getEndedAt(), entity.getDistanceKm());
        segment.setId(entity.getId());
        return segment;
    }
}
