package com.guardiants.platform.commands.interfaces.rest;

import com.guardiants.platform.commands.application.commandservices.LocationShareLinkCommandService;
import com.guardiants.platform.commands.interfaces.rest.resources.GenerateLocationShareLinkResource;
import com.guardiants.platform.commands.interfaces.rest.transform.GenerateLocationShareLinkCommandFromResourceAssembler;
import com.guardiants.platform.commands.interfaces.rest.transform.LocationShareLinkResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/commands/location-share-links", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Location Share Links", description = "Shareable vehicle tracking links")
public class LocationShareLinksController {

    private final LocationShareLinkCommandService locationShareLinkCommandService;
    private final MessageSource messageSource;

    public LocationShareLinksController(
            LocationShareLinkCommandService locationShareLinkCommandService,
            MessageSource messageSource) {
        this.locationShareLinkCommandService = locationShareLinkCommandService;
        this.messageSource = messageSource;
    }

    @Operation(summary = "Generate location share link",
            description = "Creates a time-limited shareable URL to track a vehicle's live location.")
    @PostMapping
    public ResponseEntity<?> generateLocationShareLink(
            @Valid @RequestBody GenerateLocationShareLinkResource resource) {
        log.debug("POST /api/v1/commands/location-share-links - vehicleId={}",
                resource.vehicleId());
        var command = GenerateLocationShareLinkCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var result = locationShareLinkCommandService.handle(command);
        return result.fold(
                link -> new ResponseEntity<>(
                        LocationShareLinkResourceFromEntityAssembler.toResourceFromEntity(link),
                        HttpStatus.CREATED),
                failure -> ResponseEntity.badRequest().build());
    }
}
