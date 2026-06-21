package com.guardiants.platform.query.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "operational_report_views")
public class OperationalReportViewPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long fleetId;

    private Instant periodStart;
    private Instant periodEnd;
    private int totalAlertsCount;
    private int totalLoansCount;
    private Instant generatedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "operational_report_view_id")
    private List<VehicleOperationalSummaryViewPersistenceEntity> vehicleSummaries = new ArrayList<>();
}
