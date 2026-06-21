package com.guardiants.platform.commands.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.commands.domain.model.entities.DeviceHealth;
import com.guardiants.platform.commands.domain.model.valueobjects.DeviceConnectionStatus;
import com.guardiants.platform.commands.infrastructure.persistence.jpa.entities.DeviceHealthPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class DeviceHealthEntityAssembler {

    public DeviceHealthPersistenceEntity toPersistenceEntityFromDomain(DeviceHealth health) {
        var entity = new DeviceHealthPersistenceEntity();
        entity.setVehicleId(health.getVehicleId());
        entity.setDeviceSerial(health.getDeviceSerial());
        entity.setModel(health.getModel());
        entity.setImei(health.getImei());
        entity.setStatus(health.getStatus() != null ? health.getStatus().name() : null);
        entity.setLastConnectionAt(health.getLastConnectionAt());
        entity.setBatteryLevelPercent(health.getBatteryLevelPercent());
        return entity;
    }

    public DeviceHealth toDomainFromPersistenceEntity(DeviceHealthPersistenceEntity entity) {
        return new DeviceHealth(
                entity.getVehicleId(),
                entity.getDeviceSerial(),
                entity.getModel(),
                entity.getImei(),
                entity.getStatus() != null ? DeviceConnectionStatus.valueOf(entity.getStatus()) : null,
                entity.getLastConnectionAt(),
                entity.getBatteryLevelPercent());
    }
}
