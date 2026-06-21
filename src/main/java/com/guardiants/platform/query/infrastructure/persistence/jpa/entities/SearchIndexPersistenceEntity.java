package com.guardiants.platform.query.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "search_indices")
public class SearchIndexPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, length = 25)
    private String entityType;

    @Column(nullable = false)
    private Long entityId;

    @Column(nullable = false, length = 200)
    private String label;

    @Column(length = 300)
    private String subtitle;

    private Double lat;
    private Double lng;
    private Instant occurredAt;
    private Instant indexedAt;
}
