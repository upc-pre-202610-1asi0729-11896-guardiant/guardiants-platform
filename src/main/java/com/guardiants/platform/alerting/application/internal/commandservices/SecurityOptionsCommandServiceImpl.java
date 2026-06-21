package com.guardiants.platform.alerting.application.internal.commandservices;

import com.guardiants.platform.alerting.application.commandservices.SecurityOptionsCommandFailure;
import com.guardiants.platform.alerting.application.commandservices.SecurityOptionsCommandService;
import com.guardiants.platform.alerting.domain.model.commands.UpdateSecurityOptionsCommand;
import com.guardiants.platform.alerting.domain.model.entities.SecurityOptions;
import com.guardiants.platform.alerting.domain.repositories.SecurityOptionsRepository;
import com.guardiants.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

@Service
public class SecurityOptionsCommandServiceImpl implements SecurityOptionsCommandService {

    private final SecurityOptionsRepository securityOptionsRepository;

    public SecurityOptionsCommandServiceImpl(SecurityOptionsRepository securityOptionsRepository) {
        this.securityOptionsRepository = securityOptionsRepository;
    }

    @Override
    public Result<SecurityOptions, SecurityOptionsCommandFailure> handle(
            UpdateSecurityOptionsCommand command) {
        var options = securityOptionsRepository.findByOwnerId(command.ownerId())
                .orElse(new SecurityOptions(command.ownerId()));
        options.update(command);
        return Result.success(securityOptionsRepository.save(options));
    }
}
