package com.guardiants.platform.billing.infrastructure.acl;

import com.guardiants.platform.billing.application.internal.outboundservices.acl.FleetDeviceService;
import com.guardiants.platform.billing.domain.model.entities.DeviceConnectionStatus;
import com.guardiants.platform.billing.domain.model.valueobjects.ConnectionValue;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class FleetDeviceServiceImpl implements FleetDeviceService {

    @Override
    public Optional<DeviceConnectionStatus> getDeviceConnectionStatusByOwnerId(Long ownerId) {
        // TODO: delegate to FleetContextFacade when Fleet BC is available
        return Optional.of(new DeviceConnectionStatus(
                ownerId, ConnectionValue.DISCONNECTED, Instant.now()));
    }
}
