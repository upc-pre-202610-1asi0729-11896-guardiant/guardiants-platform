package com.guardiants.platform.fleet.domain.repositories;

import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.fleet.domain.model.valueobjects.LoanStatus;

import java.util.List;
import java.util.Optional;

public interface VehicleLoanRepository {
    Optional<VehicleLoan> findById(Long id);
    List<VehicleLoan> findAllByFleetId(Long fleetId);
    List<VehicleLoan> findAllByFleetIdAndStatus(Long fleetId, LoanStatus status);
    Optional<VehicleLoan> findActiveByVehicleId(Long vehicleId);
    VehicleLoan save(VehicleLoan loan);
}
