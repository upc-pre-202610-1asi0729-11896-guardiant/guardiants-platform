package com.guardiants.platform.fleet.application.internal.commandservices;

import com.guardiants.platform.fleet.application.commandservices.VehicleLoanCommandFailure;
import com.guardiants.platform.fleet.application.commandservices.VehicleLoanCommandService;
import com.guardiants.platform.fleet.application.internal.eventhandlers.VehicleAssignedToPersonnelEventHandler;
import com.guardiants.platform.fleet.domain.model.aggregates.VehicleLoan;
import com.guardiants.platform.fleet.domain.model.commands.ApproveVehicleLoanCommand;
import com.guardiants.platform.fleet.domain.model.commands.ConfirmVehicleReturnCommand;
import com.guardiants.platform.fleet.domain.model.commands.RejectVehicleLoanCommand;
import com.guardiants.platform.fleet.domain.model.commands.RequestVehicleLoanCommand;
import com.guardiants.platform.fleet.domain.model.commands.RequestVehicleReturnCommand;
import com.guardiants.platform.fleet.domain.model.events.VehicleReleasedToFleetPoolEvent;
import com.guardiants.platform.fleet.domain.repositories.VehicleLoanRepository;
import com.guardiants.platform.fleet.domain.repositories.VehicleRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class VehicleLoanCommandServiceImpl implements VehicleLoanCommandService {

    private final VehicleLoanRepository vehicleLoanRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleAssignedToPersonnelEventHandler assignedEventHandler;
    private final ApplicationEventPublisher eventPublisher;

    public VehicleLoanCommandServiceImpl(VehicleLoanRepository vehicleLoanRepository,
                                         VehicleRepository vehicleRepository,
                                         VehicleAssignedToPersonnelEventHandler assignedEventHandler,
                                         ApplicationEventPublisher eventPublisher) {
        this.vehicleLoanRepository = vehicleLoanRepository;
        this.vehicleRepository = vehicleRepository;
        this.assignedEventHandler = assignedEventHandler;
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
                        loan.assignToPersonnel();
                        var saved = vehicleLoanRepository.save(loan);
                        vehicleRepository.findById(saved.getVehicleId())
                                .ifPresent(v -> {
                                    v.markOnLoan();
                                    vehicleRepository.save(v);
                                });
                        assignedEventHandler.publish(saved);
                        return Result.<VehicleLoan, VehicleLoanCommandFailure>success(saved);
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

    @Override
    public Result<VehicleLoan, VehicleLoanCommandFailure> handle(
            RequestVehicleReturnCommand command) {
        return vehicleLoanRepository.findById(command.loanId())
                .map(loan -> {
                    try {
                        loan.requestReturn();
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
    public Result<VehicleLoan, VehicleLoanCommandFailure> handle(
            ConfirmVehicleReturnCommand command) {
        return vehicleLoanRepository.findById(command.loanId())
                .map(loan -> {
                    try {
                        loan.confirmReturn();
                        var saved = vehicleLoanRepository.save(loan);
                        vehicleRepository.findById(loan.getVehicleId())
                                .ifPresent(v -> {
                                    v.markAvailable();
                                    vehicleRepository.save(v);
                                });
                        eventPublisher.publishEvent(
                                new VehicleReleasedToFleetPoolEvent(saved.getId(),
                                        saved.getVehicleId()));
                        return Result.<VehicleLoan, VehicleLoanCommandFailure>success(saved);
                    } catch (IllegalStateException e) {
                        return Result.<VehicleLoan, VehicleLoanCommandFailure>failure(
                                new VehicleLoanCommandFailure.InvalidStatusTransition());
                    }
                })
                .orElse(Result.failure(new VehicleLoanCommandFailure.LoanNotFound()));
    }
}
