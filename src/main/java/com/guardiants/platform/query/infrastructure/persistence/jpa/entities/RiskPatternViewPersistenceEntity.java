package com.guardiants.platform.query.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "risk_pattern_views")
public class RiskPatternViewPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(length = 25)
    private String type;

    private int occurrences;
    private Instant lastDetectedAt;
}
