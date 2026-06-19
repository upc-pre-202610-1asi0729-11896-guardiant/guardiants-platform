package com.guardiants.platform.billing.application.queryservices;

import com.guardiants.platform.billing.domain.model.entities.DeviceConnectionStatus;
import com.guardiants.platform.billing.domain.model.queries.GetDeviceConnectionStatusQuery;

import java.util.Optional;

public interface DeviceConnectionStatusQueryService {
    Optional<DeviceConnectionStatus> handle(GetDeviceConnectionStatusQuery query);
}
