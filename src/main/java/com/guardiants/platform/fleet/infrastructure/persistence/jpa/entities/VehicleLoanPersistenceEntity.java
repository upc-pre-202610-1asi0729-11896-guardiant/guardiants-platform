package com.guardiants.platform.fleet.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "vehicle_loans")
public class VehicleLoanPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private Long fleetId;

    @Column(nullable = false)
    private Long requestedByPersonnelId;

    private Long approvedByApproverId;

    @Column(nullable = false, length = 20)
    private String status;

    private Instant requestedAt;
    private Instant decidedAt;
    private Instant assignedAt;
    private Instant returnRequestedAt;
    private Instant returnConfirmedAt;

    @Column(length = 300)
    private String rejectionReason;

    private Instant expectedReturnDate;
}
