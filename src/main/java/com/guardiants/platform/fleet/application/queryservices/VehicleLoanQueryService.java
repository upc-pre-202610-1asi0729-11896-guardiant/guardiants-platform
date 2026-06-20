package com.guardiants.platform.fleet.application.queryservices;

import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.fleet.domain.model.queries.GetActiveLoanByVehicleIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetAllLoansByFleetIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetVehicleLoanByIdQuery;

import java.util.List;
import java.util.Optional;

public interface VehicleLoanQueryService {
    Optional<VehicleLoan> handle(GetVehicleLoanByIdQuery query);
    List<VehicleLoan> handle(GetAllLoansByFleetIdQuery query);
    Optional<VehicleLoan> handle(GetActiveLoanByVehicleIdQuery query);
}
