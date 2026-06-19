package com.guardiants.platform.fleet.domain.model.aggregates;

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

    public boolean isPending() { return status == LoanStatus.REQUESTED; }

    public boolean isOverdue() {
        return expectedReturnDate != null
                && Instant.now().isAfter(expectedReturnDate)
                && status != LoanStatus.RETURNED;
    }
}