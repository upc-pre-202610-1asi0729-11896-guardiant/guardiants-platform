package com.guardiants.platform.billing.interfaces.rest.transform;

import com.guardiants.platform.billing.domain.model.entities.DeviceConnectionStatus;
import com.guardiants.platform.billing.interfaces.rest.resources.DeviceConnectionStatusResource;

public class DeviceConnectionStatusResourceFromEntityAssembler {

    public static DeviceConnectionStatusResource toResourceFromEntity(
            DeviceConnectionStatus status) {
        return new DeviceConnectionStatusResource(
                status.getValue().name(),
                status.getLastSeenAt());
    }
}
