package com.guardiants.platform.iam.interfaces.rest;

import com.guardiants.platform.iam.application.commandservices.UserCommandService;
import com.guardiants.platform.iam.application.queryservices.UserQueryService;
import com.guardiants.platform.iam.domain.model.queries.GetUserByIdQuery;
import com.guardiants.platform.iam.interfaces.rest.resources.ProfileUpdateResource;
import com.guardiants.platform.iam.interfaces.rest.transform.ResponseEntityFromUserCommandResultAssembler;
import com.guardiants.platform.iam.interfaces.rest.transform.ResponseEntityFromUserQueryResultAssembler;
import com.guardiants.platform.iam.interfaces.rest.transform.UpdateProfileCommandFromResourceAssembler;
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

    public UsersController(UserQueryService userQueryService,
                           MessageSource messageSource,
                           UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.messageSource = messageSource;
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
}