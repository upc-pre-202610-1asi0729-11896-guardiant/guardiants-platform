package com.guardiants.platform.fleet.application.internal.queryservices;

import com.guardiants.platform.fleet.application.queryservices.VehicleLoanQueryService;
import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.fleet.domain.model.queries.GetActiveLoanByVehicleIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetAllLoansByFleetIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetVehicleLoanByIdQuery;
import com.guardiants.platform.fleet.domain.repositories.VehicleLoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleLoanQueryServiceImpl implements VehicleLoanQueryService {

    private final VehicleLoanRepository vehicleLoanRepository;

    public VehicleLoanQueryServiceImpl(VehicleLoanRepository vehicleLoanRepository) {
        this.vehicleLoanRepository = vehicleLoanRepository;
    }

    @Override
    public Optional<VehicleLoan> handle(GetVehicleLoanByIdQuery query) {
        return vehicleLoanRepository.findById(query.loanId());
    }

    @Override
    public List<VehicleLoan> handle(GetAllLoansByFleetIdQuery query) {
        return query.status() != null
                ? vehicleLoanRepository.findAllByFleetIdAndStatus(query.fleetId(), query.status())
                : vehicleLoanRepository.findAllByFleetId(query.fleetId());
    }

    @Override
    public Optional<VehicleLoan> handle(GetActiveLoanByVehicleIdQuery query) {
        return vehicleLoanRepository.findActiveByVehicleId(query.vehicleId());
    }
}
