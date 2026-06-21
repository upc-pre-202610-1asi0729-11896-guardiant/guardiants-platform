package com.guardiants.platform.commands.application.queryservices;

import com.guardiants.platform.commands.domain.model.entities.DeviceHealth;
import com.guardiants.platform.commands.domain.model.queries.GetDeviceHealthQuery;
import java.util.Optional;

public interface DeviceHealthQueryService {
    Optional<DeviceHealth> handle(GetDeviceHealthQuery query);
}
