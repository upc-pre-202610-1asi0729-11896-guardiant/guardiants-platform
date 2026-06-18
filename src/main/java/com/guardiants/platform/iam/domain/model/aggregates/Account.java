package com.guardiants.platform.iam.domain.model.aggregates;

import com.guardiants.platform.iam.domain.model.commands.RegisterAccountCommand;
import com.guardiants.platform.iam.domain.model.valueobjects.ProfileType;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Account aggregate root — owns authentication identity (email, password hash, profile type).
 * Separated from User to keep profile concerns (name, preferences) out of auth.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account extends AbstractDomainAggregateRoot<Account> {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProfileType profileType;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Column(length = 100)
    private String verificationToken;

    /**
     * Constructor invoked by RegisterAccountCommand.
     * Password must already be hashed by the command service before calling this.
     */
    public Account(RegisterAccountCommand command,
                   String passwordHash,
                   String verificationToken) {
        this.email = command.email();
        this.passwordHash = passwordHash;
        this.profileType = command.profileType();
        this.emailVerified = false;
        this.verificationToken = verificationToken;
    }

    public boolean isOrganization() {
        return profileType == ProfileType.COMPANY
                || profileType == ProfileType.GOVERNMENT;
    }
}