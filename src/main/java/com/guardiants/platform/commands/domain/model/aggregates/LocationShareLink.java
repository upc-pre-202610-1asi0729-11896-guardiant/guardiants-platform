package com.guardiants.platform.commands.domain.model.aggregates;

import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

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
