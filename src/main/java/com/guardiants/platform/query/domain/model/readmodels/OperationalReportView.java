package com.guardiants.platform.query.domain.model.readmodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class OperationalReportView {
    private Long id;
    private Long fleetId;
    private Instant periodStart;
    private Instant periodEnd;
    // vehicleSummaries added in feature 3
    private int totalAlertsCount;
    private int totalLoansCount;
    private Instant generatedAt;
    public boolean hasData() { return fleetId != null; }
}
