package com.guardiants.platform.alerting.interfaces.rest;

import com.guardiants.platform.alerting.application.commandservices.NotificationPreferencesCommandService;
import com.guardiants.platform.alerting.domain.model.commands.UpdateNotificationPreferencesCommand;
import com.guardiants.platform.alerting.interfaces.rest.resources.UpdateNotificationPreferencesResource;
import com.guardiants.platform.alerting.interfaces.rest.transform.ResponseEntityFromNotificationPreferencesCommandResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/notification-preferences", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Notification Preferences", description = "Per-owner push notification preferences")
public class NotificationPreferencesController {

    private final NotificationPreferencesCommandService notificationPreferencesCommandService;
    private final MessageSource messageSource;

    public NotificationPreferencesController(
            NotificationPreferencesCommandService notificationPreferencesCommandService,
            MessageSource messageSource) {
        this.notificationPreferencesCommandService = notificationPreferencesCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Update notification preferences",
            description = "Enables or disables push notification types per owner.")
    @PutMapping("/{ownerId}")
    public ResponseEntity<?> updateNotificationPreferences(
            @PathVariable Long ownerId,
            @Valid @RequestBody UpdateNotificationPreferencesResource resource) {
        log.debug("PUT /api/v1/notification-preferences/{}", ownerId);
        var command = new UpdateNotificationPreferencesCommand(
                ownerId,
                resource.securityAlertsEnabled(),
                resource.liveLocationEnabled(),
                resource.maintenanceRemindersEnabled());
        var result = notificationPreferencesCommandService.handle(command);
        return ResponseEntityFromNotificationPreferencesCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}
