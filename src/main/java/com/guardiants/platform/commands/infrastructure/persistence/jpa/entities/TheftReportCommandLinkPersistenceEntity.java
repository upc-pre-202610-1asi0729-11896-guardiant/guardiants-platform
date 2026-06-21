package com.guardiants.platform.commands.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "theft_report_command_links")
public class TheftReportCommandLinkPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private Long theftReportId;

    @Column(nullable = false)
    private Long commandId;
}
