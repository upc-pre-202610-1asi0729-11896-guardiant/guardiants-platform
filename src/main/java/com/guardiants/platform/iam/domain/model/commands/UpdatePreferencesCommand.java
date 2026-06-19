package com.guardiants.platform.iam.domain.model.commands;

import com.guardiants.platform.iam.domain.model.valueobjects.LanguageValue;
import com.guardiants.platform.iam.domain.model.valueobjects.ThemeValue;

public record UpdatePreferencesCommand(
        Long userId,
        LanguageValue language,
        ThemeValue theme) {
}