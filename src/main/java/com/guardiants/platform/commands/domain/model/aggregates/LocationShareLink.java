package com.guardiants.platform.commands.domain.model.aggregates;

import com.guardiants.platform.commands.domain.model.commands.GenerateLocationShareLinkCommand;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "location_share_links")
public class LocationShareLink extends AbstractDomainAggregateRoot<LocationShareLink> {

    @Column(nullable = false)
    private Long vehicleId;

    @Column(nullable = false)
    private Long createdByUserId;

    @Column(nullable = false, unique = true, length = 100)
    private String token;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant expiresAt;

    public LocationShareLink(GenerateLocationShareLinkCommand command) {
        this.vehicleId = command.vehicleId();
        this.createdByUserId = command.createdByUserId();
        this.token = UUID.randomUUID().toString();
        this.createdAt = Instant.now();
        this.expiresAt = command.expiresAt() != null
                ? command.expiresAt()
                : Instant.now().plus(24, ChronoUnit.HOURS);
    }

    /** Reconstruction constructor used by persistence assemblers. */
    public LocationShareLink(Long vehicleId, Long createdByUserId, String token,
                             Instant createdAt, Instant expiresAt) {
        this.vehicleId = vehicleId;
        this.createdByUserId = createdByUserId;
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public boolean isExpired() { return Instant.now().isAfter(expiresAt); }

    public String toShareableUrl() {
        return "https://track.guardiants.com/share/" + token;
    }
}
