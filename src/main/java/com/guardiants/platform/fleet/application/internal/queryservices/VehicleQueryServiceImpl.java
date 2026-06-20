package com.guardiants.platform.fleet.application.internal.queryservices;

import com.guardiants.platform.fleet.application.queryservices.VehicleQueryService;
import com.guardiants.platform.fleet.domain.model.aggregates.Vehicle;
import com.guardiants.platform.fleet.domain.model.queries.ExistsVehicleByIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetVehicleByIdQuery;
import com.guardiants.platform.fleet.domain.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehicleQueryServiceImpl implements VehicleQueryService {

    private final VehicleRepository vehicleRepository;

    public VehicleQueryServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Optional<Vehicle> handle(GetVehicleByIdQuery query) {
        return vehicleRepository.findById(query.vehicleId());
    }

    @Override
    public boolean handle(ExistsVehicleByIdQuery query) {
        return vehicleRepository.existsById(query.vehicleId());
    }
}
