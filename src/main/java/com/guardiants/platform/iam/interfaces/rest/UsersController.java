package com.guardiants.platform.iam.interfaces.rest;

import com.guardiants.platform.iam.application.commandservices.UserCommandService;
import com.guardiants.platform.iam.application.queryservices.UserQueryService;
import com.guardiants.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.guardiants.platform.iam.interfaces.rest.resources.PasswordChangeResource;
import com.guardiants.platform.iam.interfaces.rest.resources.PreferencesUpdateResource;
import com.guardiants.platform.iam.interfaces.rest.resources.ProfileUpdateResource;
import com.guardiants.platform.iam.interfaces.rest.transform.*;
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
@RequestMapping(value = "/api/v1/iam/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User profile and preferences management")
public class UsersController {

    private final UserQueryService userQueryService;
    private final MessageSource messageSource;
    private final UserCommandService userCommandService;

    public UsersController(UserQueryService userQueryService,
                           MessageSource messageSource,
                           UserCommandService userCommandService, UserCommandService userCommandService1) {
        this.userQueryService = userQueryService;
        this.messageSource = messageSource;
        this.userCommandService = userCommandService;
    }

    @Operation(summary = "Get current user", description = "Returns the authenticated user profile.")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getCurrentUser(@PathVariable Long userId) {
        log.debug("GET /api/v1/iam/users/{}", userId);
        var user = userQueryService.handle(new GetUserByIdQuery(userId));
        if (user.isEmpty()) {
            return ResponseEntityFromUserQueryResultAssembler.notFound(
                    messageSource, "iam.error.invalidCredentials", userId);
        }
        return ResponseEntityFromUserQueryResultAssembler.toResponseEntityFromUser(user);
    }

    @Operation(summary = "Update profile", description = "Updates the user name and/or email.")
    @PutMapping("/{userId}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable Long userId,
                                           @Valid @RequestBody ProfileUpdateResource resource) {
        log.debug("PUT /api/v1/iam/users/{}/profile", userId);
        var command = UpdateProfileCommandFromResourceAssembler
                .toCommandFromResource(userId, resource);
        var result = userCommandService.handle(command);
        return ResponseEntityFromUserCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Change password", description = "Changes the user password after verifying the current one.")
    @PatchMapping("/{userId}/password")
    public ResponseEntity<?> changePassword(@PathVariable Long userId,
                                            @Valid @RequestBody PasswordChangeResource resource) {
        log.debug("PATCH /api/v1/iam/users/{}/password", userId);
        var command = ChangePasswordCommandFromResourceAssembler
                .toCommandFromResource(userId, resource);
        var result = userCommandService.handle(command);
        return ResponseEntityFromUserCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }

    @Operation(summary = "Update preferences", description = "Updates the user language and theme preferences.")
    @PatchMapping("/{userId}/preferences")
    public ResponseEntity<?> updatePreferences(@PathVariable Long userId,
                                               @Valid @RequestBody PreferencesUpdateResource resource) {
        log.debug("PATCH /api/v1/iam/users/{}/preferences", userId);
        var command = UpdatePreferencesCommandFromResourceAssembler
                .toCommandFromResource(userId, resource);
        var result = userCommandService.handle(command);
        return ResponseEntityFromUserCommandResultAssembler
                .toResponseEntityFromResult(result, messageSource);
    }
}