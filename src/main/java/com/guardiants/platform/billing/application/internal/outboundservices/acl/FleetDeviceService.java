// FleetDeviceService.java
package com.guardiants.platform.billing.application.internal.outboundservices.acl;
import com.guardiants.platform.billing.domain.model.entities.DeviceConnectionStatus;
import java.util.Optional;

public interface FleetDeviceService {
    Optional<DeviceConnectionStatus> getDeviceConnectionStatusByOwnerId(Long ownerId);
}