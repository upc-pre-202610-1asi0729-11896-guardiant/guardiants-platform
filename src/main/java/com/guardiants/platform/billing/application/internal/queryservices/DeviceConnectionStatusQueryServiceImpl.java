package com.guardiants.platform.billing.application.internal.queryservices;

import com.guardiants.platform.billing.application.internal.outboundservices.acl.FleetDeviceService;
import com.guardiants.platform.billing.application.queryservices.DeviceConnectionStatusQueryService;
import com.guardiants.platform.billing.domain.model.entities.DeviceConnectionStatus;
import com.guardiants.platform.billing.domain.model.queries.GetDeviceConnectionStatusQuery;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceConnectionStatusQueryServiceImpl implements DeviceConnectionStatusQueryService {

    private final FleetDeviceService fleetDeviceService;

    public DeviceConnectionStatusQueryServiceImpl(FleetDeviceService fleetDeviceService) {
        this.fleetDeviceService = fleetDeviceService;
    }

    @Override
    public Optional<DeviceConnectionStatus> handle(GetDeviceConnectionStatusQuery query) {
        return fleetDeviceService.getDeviceConnectionStatusByOwnerId(query.ownerId());
    }
}
