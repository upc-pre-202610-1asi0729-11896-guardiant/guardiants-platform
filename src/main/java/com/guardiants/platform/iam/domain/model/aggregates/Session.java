package com.guardiants.platform.iam.domain.model.aggregates;

import com.guardiants.platform.iam.domain.model.commands.LoginCommand;
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

    /**
     * Sets the userId after construction.
     * Used by the LoginCommand handler once the User is resolved from the Account.
     */
    public void setUserId(Long userId) { this.userId = userId; }

    /**
     * Reconstitution factory — used ONLY by persistence assemblers to rebuild
     * a Session from a database row without going through the LoginCommand constructor.
     */
    public static Session reconstitute(Long id, Long userId, String accessToken,
                                       String refreshToken, Instant issuedAt,
                                       Instant expiresAt, SessionStatus status) {
        var session = new Session();
        session.setId(id);
        session.userId = userId;
        session.accessToken = accessToken;
        session.refreshToken = refreshToken;
        session.issuedAt = issuedAt;
        session.expiresAt = expiresAt;
        session.status = status;
        return session;
    }
    public boolean isActive() { return status == SessionStatus.ACTIVE; }
    public boolean isExpired() { return status == SessionStatus.EXPIRED
            || Instant.now().isAfter(expiresAt); }

    public long remainingMinutes() {
        if (isExpired()) return 0;
        return java.time.Duration.between(Instant.now(), expiresAt).toMinutes();
    }
    public Session(LoginCommand command, String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.issuedAt = Instant.now();
        this.expiresAt = Instant.now().plus(7, java.time.temporal.ChronoUnit.DAYS);
        this.status = SessionStatus.ACTIVE;
    }

    public void refresh(String newAccessToken, Instant newExpiresAt) {
        this.accessToken = newAccessToken;
        this.expiresAt = newExpiresAt;
    }

    public void close() { this.status = SessionStatus.CLOSED; }
    public void expire() { this.status = SessionStatus.EXPIRED; }
}
