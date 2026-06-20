package com.guardiants.platform.fleet.domain.model.events;

public record IoTDeviceAssignedEvent(Long vehicleId, String deviceSerial) {}
