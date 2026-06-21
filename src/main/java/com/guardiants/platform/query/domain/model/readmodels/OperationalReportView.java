package com.guardiants.platform.query.domain.model.readmodels;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OperationalReportView {
    private Long id;
    private Long fleetId;
    private Instant periodStart;
    private Instant periodEnd;
    private List<VehicleOperationalSummaryView> vehicleSummaries = new ArrayList<>();
    private int totalAlertsCount;
    private int totalLoansCount;
    private Instant generatedAt;
    public boolean hasData() { return fleetId != null; }
}
