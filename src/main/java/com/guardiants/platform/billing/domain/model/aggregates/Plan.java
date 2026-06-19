package com.guardiants.platform.billing.domain.model.aggregates;

import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

/**
 * Plan aggregate root. Immutable once created — plans are managed by ops,
 * not through REST commands by end users.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "plans")
public class Plan extends AbstractDomainAggregateRoot<Plan> {

    @Column(nullable = false, unique = true, length = 30)
    private String key;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double priceUsd;

    @Column(nullable = false)
    private int billingIntervalMonths;

    @Column(nullable = false)
    private int maxVehicles;

    /**
     * Stored as a JSON string: ["Feature A","Feature B"].
     * Deserialized in the assembler.
     */
    @Column(nullable = false, length = 1000)
    private String featuresJson;

    public Plan(String key, String name, double priceUsd,
                int billingIntervalMonths, int maxVehicles, String featuresJson) {
        this.key = key;
        this.name = name;
        this.priceUsd = priceUsd;
        this.billingIntervalMonths = billingIntervalMonths;
        this.maxVehicles = maxVehicles;
        this.featuresJson = featuresJson;
    }

    public boolean isFree() { return priceUsd == 0.0; }

    public boolean supportsVehicleCount(int count) { return count <= maxVehicles; }
}