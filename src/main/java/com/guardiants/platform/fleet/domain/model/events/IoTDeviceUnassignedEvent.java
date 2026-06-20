package com.guardiants.platform.fleet.domain.model.events;

public record IoTDeviceUnassignedEvent(Long vehicleId, String deviceSerial) {}
