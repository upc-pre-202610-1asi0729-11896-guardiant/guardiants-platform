package com.guardiants.platform.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class UserPreferences {

    @Enumerated(EnumType.STRING)
    private LanguageValue language = LanguageValue.ES;

    @Enumerated(EnumType.STRING)
    private ThemeValue theme = ThemeValue.LIGHT;

    public UserPreferences() {}

    public UserPreferences(LanguageValue language, ThemeValue theme) {
        this.language = language;
        this.theme = theme;
    }

    public LanguageValue getLanguage() { return language; }
    public ThemeValue getTheme() { return theme; }

    public boolean isDarkMode() { return theme == ThemeValue.DARK; }
}