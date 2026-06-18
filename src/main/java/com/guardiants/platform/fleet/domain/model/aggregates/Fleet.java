package com.guardiants.platform.fleet.domain.model.aggregates;

import com.guardiants.platform.fleet.domain.model.commands.CreateFleetCommand;
import com.guardiants.platform.fleet.domain.model.valueobjects.OrganizationType;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "fleets")
public class Fleet extends AbstractDomainAggregateRoot<Fleet> {

    @Column(nullable = false)
    private Long ownerId;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private OrganizationType organizationType;

    public Fleet(CreateFleetCommand command) {
        this.ownerId = command.ownerId();
        this.name = command.name();
        this.organizationType = command.organizationType();
    }

    public boolean isGovernmentOrCompany() {
        return organizationType == OrganizationType.COMPANY
                || organizationType == OrganizationType.GOVERNMENT;
    }
}