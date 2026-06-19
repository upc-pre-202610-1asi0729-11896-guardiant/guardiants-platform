package com.guardiants.platform.fleet.domain.model.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "device_assignments")
public class DeviceAssignment extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false, length = 100)
    private String deviceSerial;

    @Column(nullable = false)
    private Instant assignedAt;

    private Instant unassignedAt;

    public DeviceAssignment(Long vehicleId, String deviceSerial) {
        this.vehicleId = vehicleId;
        this.deviceSerial = deviceSerial;
        this.assignedAt = Instant.now();
    }

    public boolean isActive() { return unassignedAt == null; }

    public void unassign() { this.unassignedAt = Instant.now(); }
}