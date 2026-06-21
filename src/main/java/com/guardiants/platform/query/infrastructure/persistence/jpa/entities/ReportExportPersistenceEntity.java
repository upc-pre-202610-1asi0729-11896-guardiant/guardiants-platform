package com.guardiants.platform.query.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "report_exports")
public class ReportExportPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long sourceReportId;

    @Column(nullable = false, length = 5)
    private String format;

    @Column(nullable = false)
    private Instant generatedAt;

    @Column(nullable = false)
    private String downloadUrl;
}
