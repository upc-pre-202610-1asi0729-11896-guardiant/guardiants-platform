package com.guardiants.platform.alerting.domain.model.entities;

import com.guardiants.platform.alerting.domain.model.valueobjects.AlertType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "notification_preferences")
public class NotificationPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long ownerId;

    @Column(nullable = false)
    private boolean securityAlertsEnabled = true;

    @Column(nullable = false)
    private boolean liveLocationEnabled = true;

    @Column(nullable = false)
    private boolean maintenanceRemindersEnabled = true;

    @Column(nullable = false)
    private Instant updatedAt;

    public NotificationPreferences(Long ownerId) {
        this.ownerId = ownerId;
        this.updatedAt = Instant.now();
    }

    public boolean allows(AlertType alertType) {
        return securityAlertsEnabled;
    }
}
