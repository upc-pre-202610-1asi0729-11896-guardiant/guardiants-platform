package com.guardiants.platform.alerting.domain.model.aggregates;

import com.guardiants.platform.alerting.domain.model.valueobjects.DefenseActionResult;
import com.guardiants.platform.alerting.domain.model.valueobjects.DefenseActionType;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "automatic_defense_actions")
public class AutomaticDefenseAction extends AbstractDomainAggregateRoot<AutomaticDefenseAction> {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private Long triggeredByAlertId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private DefenseActionType actionType;

    private Long commandId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private DefenseActionResult result;

    @Column(nullable = false)
    private Instant triggeredAt;

    private Instant deactivatedAt;
}
