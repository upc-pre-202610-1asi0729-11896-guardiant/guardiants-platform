package com.guardiants.platform.iam.interfaces.rest.transform;

import com.guardiants.platform.iam.domain.model.commands.UpdatePreferencesCommand;
import com.guardiants.platform.iam.domain.model.valueobjects.LanguageValue;
import com.guardiants.platform.iam.domain.model.valueobjects.ThemeValue;
import com.guardiants.platform.iam.interfaces.rest.resources.PreferencesUpdateResource;

public class UpdatePreferencesCommandFromResourceAssembler {
    public static UpdatePreferencesCommand toCommandFromResource(Long userId,
                                                                 PreferencesUpdateResource resource) {
        return new UpdatePreferencesCommand(
                userId,
                resource.language() != null ? LanguageValue.valueOf(resource.language()) : null,
                resource.theme() != null ? ThemeValue.valueOf(resource.theme()) : null);
    }
}
