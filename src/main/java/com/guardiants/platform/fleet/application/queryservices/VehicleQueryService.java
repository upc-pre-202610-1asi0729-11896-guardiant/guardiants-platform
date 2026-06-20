package com.guardiants.platform.fleet.application.queryservices;

import com.guardiants.platform.fleet.domain.model.aggregates.Vehicle;
import com.guardiants.platform.fleet.domain.model.queries.ExistsVehicleByIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetAllVehiclesByFleetIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetVehicleByIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetVehicleByPlateQuery;

import java.util.List;
import java.util.Optional;

public interface VehicleQueryService {
    Optional<Vehicle> handle(GetVehicleByIdQuery query);
    List<Vehicle> handle(GetAllVehiclesByFleetIdQuery query);
    Optional<Vehicle> handle(GetVehicleByPlateQuery query);
    boolean handle(ExistsVehicleByIdQuery query);
}
