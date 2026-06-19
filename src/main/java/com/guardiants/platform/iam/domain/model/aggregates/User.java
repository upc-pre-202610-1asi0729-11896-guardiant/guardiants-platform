package com.guardiants.platform.iam.domain.model.aggregates;

import com.guardiants.platform.iam.domain.model.valueobjects.LanguageValue;
import com.guardiants.platform.iam.domain.model.valueobjects.ProfileType;
import com.guardiants.platform.iam.domain.model.valueobjects.ThemeValue;
import com.guardiants.platform.iam.domain.model.valueobjects.UserPreferences;
import com.guardiants.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * User aggregate root — owns profile data (name, email copy, preferences).
 * Created automatically when an Account is registered.
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractDomainAggregateRoot<User> {

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProfileType profileType;

    @Embedded
    private UserPreferences preferences = new UserPreferences();

    /**
     * Constructor — called by AccountCommandServiceImpl after saving the Account.
     */
    public User(Account account) {
        this.accountId = account.getId();
        this.name = "";   // name provided via UpdateProfileCommand after verification
        this.email = account.getEmail();
        this.profileType = account.getProfileType();
        this.preferences = new UserPreferences();
    }

    public String displayName() {
        return name.isBlank() ? email : name;
    }

    public void updateProfile(String name, String email) {
        if (name != null && !name.isBlank()) this.name = name;
        if (email != null && !email.isBlank()) this.email = email;
    }

    public void updatePreferences(LanguageValue language, ThemeValue theme) {
        this.preferences = new UserPreferences(
                language != null ? language : this.preferences.getLanguage(),
                theme != null ? theme : this.preferences.getTheme());
    }
}