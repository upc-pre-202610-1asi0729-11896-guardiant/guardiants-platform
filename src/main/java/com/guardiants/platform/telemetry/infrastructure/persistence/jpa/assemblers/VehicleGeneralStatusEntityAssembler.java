package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.telemetry.domain.model.aggregates.VehicleGeneralStatus;
import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.entities.VehicleGeneralStatusPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class VehicleGeneralStatusEntityAssembler {

    public VehicleGeneralStatusPersistenceEntity toPersistenceEntityFromDomain(
            VehicleGeneralStatus status) {
        var entity = new VehicleGeneralStatusPersistenceEntity();
        entity.setId(status.getId());
        entity.setVehicleId(status.getVehicleId());
        entity.setLatestPointId(status.getLatestPointId());
        entity.setActiveAlertCount(status.getActiveAlertCount());
        entity.setLocked(status.isLocked());
        entity.setLastUpdatedAt(status.getLastUpdatedAt());
        return entity;
    }

    public VehicleGeneralStatus toDomainFromPersistenceEntity(
            VehicleGeneralStatusPersistenceEntity entity) {
        var status = new VehicleGeneralStatus(
                entity.getVehicleId(), entity.getLatestPointId(),
                entity.getActiveAlertCount(), entity.isLocked(), entity.getLastUpdatedAt());
        status.setId(entity.getId());
        return status;
    }
}
