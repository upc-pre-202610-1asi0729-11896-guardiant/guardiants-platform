package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.telemetry.domain.model.aggregates.TelemetryPoint;
import com.guardiants.platform.telemetry.domain.model.valueobjects.ConnectivityStatus;
import com.guardiants.platform.telemetry.domain.model.valueobjects.ConnectivityValue;
import com.guardiants.platform.telemetry.domain.model.valueobjects.GeoPoint;
import com.guardiants.platform.telemetry.domain.model.valueobjects.VehicleStatusSnapshot;
import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.entities.TelemetryPointPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class TelemetryPointEntityAssembler {

    public TelemetryPointPersistenceEntity toPersistenceEntityFromDomain(TelemetryPoint point) {
        var entity = new TelemetryPointPersistenceEntity();
        entity.setId(point.getId());
        entity.setVehicleId(point.getVehicleId());
        entity.setDeviceSerial(point.getDeviceSerial());
        entity.setTimestamp(point.getTimestamp());
        entity.setLat(point.getLocation().lat());
        entity.setLng(point.getLocation().lng());
        var status = point.getVehicleStatus();
        entity.setSpeedKmh(status.speedKmh());
        entity.setFuelLevelPercent(status.fuelLevelPercent());
        entity.setEngineTemperatureC(status.engineTemperatureC());
        entity.setBatteryLevelPercent(status.batteryLevelPercent());
        entity.setOdometerKm(status.odometerKm());
        entity.setRpm(status.rpm());
        entity.setEngineOn(status.engineOn());
        entity.setConnectivityStatus(point.getConnectivity().getValue().name());
        entity.setConnectivityLastSeenAt(point.getConnectivity().getLastSeenAt());
        return entity;
    }

    public TelemetryPoint toDomainFromPersistenceEntity(TelemetryPointPersistenceEntity entity) {
        var vehicleStatus = new VehicleStatusSnapshot(
                entity.getSpeedKmh(), entity.getFuelLevelPercent(),
                entity.getEngineTemperatureC(), entity.getBatteryLevelPercent(),
                entity.getOdometerKm(), entity.getRpm(), entity.isEngineOn());
        var connectivity = new ConnectivityStatus(
                ConnectivityValue.valueOf(entity.getConnectivityStatus()),
                entity.getConnectivityLastSeenAt());
        var point = new TelemetryPoint(
                entity.getVehicleId(), entity.getDeviceSerial(), entity.getTimestamp(),
                new GeoPoint(entity.getLat(), entity.getLng()), vehicleStatus, connectivity);
        point.setId(entity.getId());
        return point;
    }
}
