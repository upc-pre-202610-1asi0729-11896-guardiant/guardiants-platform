package com.guardiants.platform.billing.infrastructure.persistence.jpa.entities;

import com.guardiants.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "plans")
public class PlanPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "plan_key", nullable = false, unique = true, length = 30)
    private String key;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double priceUsd;

    @Column(nullable = false)
    private int billingIntervalMonths;

    @Column(nullable = false)
    private int maxVehicles;

    // Stored as JSON string: ["Feature A","Feature B"]
    @Column(nullable = false, length = 1000)
    private String featuresJson;
}