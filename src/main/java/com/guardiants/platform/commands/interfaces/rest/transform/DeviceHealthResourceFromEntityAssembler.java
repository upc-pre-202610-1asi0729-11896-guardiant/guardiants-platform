package com.guardiants.platform.commands.interfaces.rest.transform;

import com.guardiants.platform.commands.domain.model.entities.DeviceHealth;
import com.guardiants.platform.commands.interfaces.rest.resources.DeviceHealthResource;

public class DeviceHealthResourceFromEntityAssembler {

    public static DeviceHealthResource toResourceFromEntity(DeviceHealth health) {
        return new DeviceHealthResource(health.getVehicleId(), health.getDeviceSerial(),
                health.getModel(), health.getImei(),
                health.getStatus() != null ? health.getStatus().name() : null,
                health.getLastConnectionAt(), health.getBatteryLevelPercent());
    }
}
