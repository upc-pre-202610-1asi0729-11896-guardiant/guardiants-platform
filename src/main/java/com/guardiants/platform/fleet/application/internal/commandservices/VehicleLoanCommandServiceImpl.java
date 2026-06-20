package com.guardiants.platform.fleet.application.internal.commandservices;

import com.guardiants.platform.fleet.application.commandservices.VehicleLoanCommandFailure;
import com.guardiants.platform.fleet.application.commandservices.VehicleLoanCommandService;
import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.fleet.domain.model.commands.ApproveVehicleLoanCommand;
import com.guardiants.platform.fleet.domain.model.commands.RejectVehicleLoanCommand;
import com.guardiants.platform.fleet.domain.model.commands.RequestVehicleLoanCommand;
import com.guardiants.platform.fleet.domain.repositories.VehicleLoanRepository;
import com.guardiants.platform.fleet.domain.repositories.VehicleRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class VehicleLoanCommandServiceImpl implements VehicleLoanCommandService {

    private final VehicleLoanRepository vehicleLoanRepository;
    private final VehicleRepository vehicleRepository;
    private final ApplicationEventPublisher eventPublisher;

    public VehicleLoanCommandServiceImpl(VehicleLoanRepository vehicleLoanRepository,
                                         VehicleRepository vehicleRepository,
                                         ApplicationEventPublisher eventPublisher) {
        this.vehicleLoanRepository = vehicleLoanRepository;
        this.vehicleRepository = vehicleRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Result<VehicleLoan, VehicleLoanCommandFailure> handle(
            RequestVehicleLoanCommand command) {
        return vehicleRepository.findById(command.vehicleId())
                .map(vehicle -> {
                    if (!vehicle.isAvailableForLoan()) {
                        return Result.<VehicleLoan, VehicleLoanCommandFailure>failure(
                                new VehicleLoanCommandFailure.VehicleNotAvailable());
                    }
                    var loan = new VehicleLoan(command, vehicle.getFleetId());
                    return Result.<VehicleLoan, VehicleLoanCommandFailure>success(
                            vehicleLoanRepository.save(loan));
                })
                .orElse(Result.failure(new VehicleLoanCommandFailure.VehicleNotAvailable()));
    }

    @Override
    public Result<VehicleLoan, VehicleLoanCommandFailure> handle(ApproveVehicleLoanCommand command) {
        return vehicleLoanRepository.findById(command.loanId())
                .map(loan -> {
                    try {
                        loan.approve(command.approverId());
                        return Result.<VehicleLoan, VehicleLoanCommandFailure>success(
                                vehicleLoanRepository.save(loan));
                    } catch (IllegalStateException e) {
                        return Result.<VehicleLoan, VehicleLoanCommandFailure>failure(
                                new VehicleLoanCommandFailure.InvalidStatusTransition());
                    }
                })
                .orElse(Result.failure(new VehicleLoanCommandFailure.LoanNotFound()));
    }

    @Override
    public Result<VehicleLoan, VehicleLoanCommandFailure> handle(RejectVehicleLoanCommand command) {
        return vehicleLoanRepository.findById(command.loanId())
                .map(loan -> {
                    try {
                        loan.reject(command.approverId(), command.reason());
                        return Result.<VehicleLoan, VehicleLoanCommandFailure>success(
                                vehicleLoanRepository.save(loan));
                    } catch (IllegalStateException e) {
                        return Result.<VehicleLoan, VehicleLoanCommandFailure>failure(
                                new VehicleLoanCommandFailure.InvalidStatusTransition());
                    }
                })
                .orElse(Result.failure(new VehicleLoanCommandFailure.LoanNotFound()));
    }
}
