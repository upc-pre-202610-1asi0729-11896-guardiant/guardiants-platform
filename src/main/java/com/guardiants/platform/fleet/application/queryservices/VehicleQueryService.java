package com.guardiants.platform.fleet.application.queryservices;

import com.guardiants.platform.fleet.domain.model.aggregates.Vehicle;
import com.guardiants.platform.fleet.domain.model.queries.ExistsVehicleByIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetVehicleByIdQuery;

import java.util.Optional;

public interface VehicleQueryService {
    Optional<Vehicle> handle(GetVehicleByIdQuery query);
    boolean handle(ExistsVehicleByIdQuery query);
}
