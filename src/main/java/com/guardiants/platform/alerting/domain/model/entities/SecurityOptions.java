package com.guardiants.platform.alerting.domain.model.entities;

import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import com.guardiants.platform.alerting.domain.model.commands.UpdateSecurityOptionsCommand;
import com.guardiants.platform.alerting.domain.model.valueobjects.AlertSeverity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "security_options")
public class SecurityOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long ownerId;

    @Column(nullable = false)
    private boolean suspiciousMovementEnabled = false;

    @Column(nullable = false)
    private boolean autoEngineShutdownEnabled = false;

    @Column(nullable = false)
    private boolean autoSafeModeEnabled = false;

    @Column(nullable = false)
    private Instant updatedAt;

    public SecurityOptions(Long ownerId) {
        this.ownerId = ownerId;
        this.updatedAt = Instant.now();
    }

    public void update(UpdateSecurityOptionsCommand command) {
        if (command.suspiciousMovementEnabled() != null)
            this.suspiciousMovementEnabled = command.suspiciousMovementEnabled();
        if (command.autoEngineShutdownEnabled() != null)
            this.autoEngineShutdownEnabled = command.autoEngineShutdownEnabled();
        if (command.autoSafeModeEnabled() != null)
            this.autoSafeModeEnabled = command.autoSafeModeEnabled();
        this.updatedAt = Instant.now();
    }

    public boolean shouldTriggerAutoDefense(SecurityAlert alert) {
        return autoSafeModeEnabled && alert.getSeverity() == AlertSeverity.CRITICAL;
    }
}
