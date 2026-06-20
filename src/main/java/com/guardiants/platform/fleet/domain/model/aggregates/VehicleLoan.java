package com.guardiants.platform.fleet.domain.model.aggregates;

import com.guardiants.platform.fleet.domain.model.commands.RequestVehicleLoanCommand;
import com.guardiants.platform.fleet.domain.model.valueobjects.LoanStatus;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "vehicle_loans")
public class VehicleLoan extends AbstractDomainAggregateRoot<VehicleLoan> {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private Long fleetId;

    @Column(nullable = false)
    private Long requestedByPersonnelId;

    private Long approvedByApproverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LoanStatus status = LoanStatus.REQUESTED;

    private Instant requestedAt;
    private Instant decidedAt;
    private Instant assignedAt;
    private Instant returnRequestedAt;
    private Instant returnConfirmedAt;

    @Column(length = 300)
    private String rejectionReason;

    private Instant expectedReturnDate;

    public VehicleLoan(RequestVehicleLoanCommand command, Long fleetId) {
        this.vehicleId = command.vehicleId();
        this.fleetId = fleetId;
        this.requestedByPersonnelId = command.requestedByPersonnelId();
        this.status = LoanStatus.REQUESTED;
        this.requestedAt = Instant.now();
        this.expectedReturnDate = command.expectedReturnDate();
    }

    /** Reconstruction constructor used by persistence assemblers. */
    public VehicleLoan(Long vehicleId, Long fleetId, Long requestedByPersonnelId,
                       Long approvedByApproverId, LoanStatus status, Instant requestedAt,
                       Instant decidedAt, Instant assignedAt, Instant returnRequestedAt,
                       Instant returnConfirmedAt, String rejectionReason,
                       Instant expectedReturnDate) {
        this.vehicleId = vehicleId;
        this.fleetId = fleetId;
        this.requestedByPersonnelId = requestedByPersonnelId;
        this.approvedByApproverId = approvedByApproverId;
        this.status = status;
        this.requestedAt = requestedAt;
        this.decidedAt = decidedAt;
        this.assignedAt = assignedAt;
        this.returnRequestedAt = returnRequestedAt;
        this.returnConfirmedAt = returnConfirmedAt;
        this.rejectionReason = rejectionReason;
        this.expectedReturnDate = expectedReturnDate;
    }

    public void approve(Long approverId) {
        if (status != LoanStatus.REQUESTED)
            throw new IllegalStateException("fleet.error.invalidStatusTransition");
        this.approvedByApproverId = approverId;
        this.status = LoanStatus.APPROVED;
        this.decidedAt = Instant.now();
    }

    public void reject(Long approverId, String reason) {
        if (status != LoanStatus.REQUESTED)
            throw new IllegalStateException("fleet.error.invalidStatusTransition");
        this.approvedByApproverId = approverId;
        this.rejectionReason = reason;
        this.status = LoanStatus.REJECTED;
        this.decidedAt = Instant.now();
    }

    public boolean isPending() { return status == LoanStatus.REQUESTED; }

    public boolean isOverdue() {
        return expectedReturnDate != null
                && Instant.now().isAfter(expectedReturnDate)
                && status != LoanStatus.RETURNED;
    }
}
