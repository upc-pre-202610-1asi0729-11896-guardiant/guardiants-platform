package com.guardiants.platform.iam.application.internal.eventhandlers;

import com.guardiants.platform.iam.application.commandservices.AccountCommandService;
import com.guardiants.platform.iam.domain.model.commands.RegisterAccountCommand;
import com.guardiants.platform.iam.domain.model.valueobjects.ProfileType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationReadyEventHandler {

    private final AccountCommandService accountCommandService;

    public ApplicationReadyEventHandler(AccountCommandService accountCommandService) {
        this.accountCommandService = accountCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        seedAdminAccount();
    }

    private void seedAdminAccount() {
        var command = new RegisterAccountCommand(
                "admin@guardiants.com",
                "Admin1234!",
                ProfileType.NATURAL_PERSON,
                "Admin GuardiAnts");
        var result = accountCommandService.handle(command);
        if (result.isSuccess()) {
            log.info("Admin account seeded: admin@guardiants.com");
        } else {
            log.debug("Admin account already exists — skipping seed.");
        }
    }
}
