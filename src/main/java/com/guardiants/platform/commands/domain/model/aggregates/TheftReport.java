package com.guardiants.platform.commands.domain.model.aggregates;

import com.guardiants.platform.commands.domain.model.commands.ReportTheftCommand;
import com.guardiants.platform.commands.domain.model.valueobjects.IncidentStatus;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "theft_reports")
public class TheftReport extends AbstractDomainAggregateRoot<TheftReport> {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private Long reportedByUserId;

    @Column(nullable = false)
    private Instant reportedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private IncidentStatus status;

    @Transient
    private List<Long> relatedCommandIds = new ArrayList<>();

    private Long relatedAlertId;

    public TheftReport(ReportTheftCommand command) {
        this.vehicleId = command.vehicleId();
        this.reportedByUserId = command.reportedByUserId();
        this.reportedAt = Instant.now();
        this.status = IncidentStatus.ACTIVE;
    }

    /** Reconstruction constructor used by persistence assemblers. */
    public TheftReport(Long vehicleId, Long reportedByUserId, Instant reportedAt,
                       IncidentStatus status, Long relatedAlertId) {
        this.vehicleId = vehicleId;
        this.reportedByUserId = reportedByUserId;
        this.reportedAt = reportedAt;
        this.status = status;
        this.relatedAlertId = relatedAlertId;
    }

    public void linkCommand(Long commandId) {
        this.relatedCommandIds.add(commandId);
    }

    public void resolve() { this.status = IncidentStatus.RESOLVED; }

    public boolean isActive() { return status == IncidentStatus.ACTIVE; }

    public boolean activatesEmergencyProtocol() {
        return status == IncidentStatus.ACTIVE;
    }
}
