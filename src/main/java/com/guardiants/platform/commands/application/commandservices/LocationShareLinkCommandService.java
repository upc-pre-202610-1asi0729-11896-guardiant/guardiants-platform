package com.guardiants.platform.commands.application.commandservices;

import com.guardiants.platform.commands.domain.model.aggregates.LocationShareLink;
import com.guardiants.platform.commands.domain.model.commands.GenerateLocationShareLinkCommand;
import com.guardiants.platform.shared.application.result.Result;

public interface LocationShareLinkCommandService {
    Result<LocationShareLink, LocationShareLinkCommandFailure>
            handle(GenerateLocationShareLinkCommand command);
}
