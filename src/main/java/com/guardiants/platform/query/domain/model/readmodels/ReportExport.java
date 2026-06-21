package com.guardiants.platform.query.domain.model.readmodels;

import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "report_exports")
public class ReportExport extends AbstractDomainAggregateRoot<ReportExport> {

    @Column(nullable = false)
    private Long sourceReportId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private ExportFormat format;

    @Column(nullable = false)
    private Instant generatedAt;

    @Column(nullable = false)
    private String downloadUrl;

    public ReportExport(Long sourceReportId, ExportFormat format, String downloadUrl) {
        this.sourceReportId = sourceReportId;
        this.format = format;
        this.downloadUrl = downloadUrl;
        this.generatedAt = Instant.now();
    }

    /** Reconstruction constructor used by persistence assemblers. */
    public ReportExport(Long sourceReportId, ExportFormat format, String downloadUrl,
                        Instant generatedAt) {
        this.sourceReportId = sourceReportId;
        this.format = format;
        this.downloadUrl = downloadUrl;
        this.generatedAt = generatedAt;
    }

    public boolean isReady() { return downloadUrl != null && !downloadUrl.isBlank(); }
}
