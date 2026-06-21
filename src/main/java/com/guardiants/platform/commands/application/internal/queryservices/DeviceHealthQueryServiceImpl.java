package com.guardiants.platform.commands.application.internal.queryservices;

import com.guardiants.platform.commands.application.queryservices.DeviceHealthQueryService;
import com.guardiants.platform.commands.domain.model.entities.DeviceHealth;
import com.guardiants.platform.commands.domain.model.queries.GetDeviceHealthQuery;
import com.guardiants.platform.commands.domain.repositories.DeviceHealthRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DeviceHealthQueryServiceImpl implements DeviceHealthQueryService {

    private final DeviceHealthRepository deviceHealthRepository;

    public DeviceHealthQueryServiceImpl(DeviceHealthRepository deviceHealthRepository) {
        this.deviceHealthRepository = deviceHealthRepository;
    }

    @Override
    public Optional<DeviceHealth> handle(GetDeviceHealthQuery query) {
        return deviceHealthRepository.findByVehicleId(query.vehicleId());
    }
}
