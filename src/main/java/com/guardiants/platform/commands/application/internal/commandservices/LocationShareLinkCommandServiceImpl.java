package com.guardiants.platform.commands.application.internal.commandservices;

import com.guardiants.platform.commands.application.commandservices.LocationShareLinkCommandFailure;
import com.guardiants.platform.commands.application.commandservices.LocationShareLinkCommandService;
import com.guardiants.platform.commands.domain.model.aggregates.LocationShareLink;
import com.guardiants.platform.commands.domain.model.commands.GenerateLocationShareLinkCommand;
import com.guardiants.platform.commands.domain.repositories.LocationShareLinkRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class LocationShareLinkCommandServiceImpl implements LocationShareLinkCommandService {

    private final LocationShareLinkRepository locationShareLinkRepository;

    public LocationShareLinkCommandServiceImpl(
            LocationShareLinkRepository locationShareLinkRepository) {
        this.locationShareLinkRepository = locationShareLinkRepository;
    }

    @Override
    public Result<LocationShareLink, LocationShareLinkCommandFailure> handle(
            GenerateLocationShareLinkCommand command) {
        var link = new LocationShareLink(command);
        return Result.success(locationShareLinkRepository.save(link));
    }
}
