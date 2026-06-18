package com.guardiants.platform.iam.domain.model.aggregates;

import com.guardiants.platform.iam.domain.model.valueobjects.SessionStatus;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.Instant;

/**
 * Session aggregate root — represents a stateful JWT session record.
 * Persisting sessions allows server-side logout and token rotation.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "sessions")
public class Session extends AbstractDomainAggregateRoot<Session> {

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 1000)
    private String accessToken;

    @Column(nullable = false, length = 500)
    private String refreshToken;

    @Column(nullable = false)
    private Instant issuedAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private SessionStatus status = SessionStatus.ACTIVE;

    public boolean isActive() { return status == SessionStatus.ACTIVE; }
    public boolean isExpired() { return status == SessionStatus.EXPIRED
            || Instant.now().isAfter(expiresAt); }

    public long remainingMinutes() {
        if (isExpired()) return 0;
        return java.time.Duration.between(Instant.now(), expiresAt).toMinutes();
    }
}
