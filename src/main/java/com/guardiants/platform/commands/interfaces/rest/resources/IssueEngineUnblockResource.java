package com.guardiants.platform.commands.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record IssueEngineUnblockResource(@NotNull Long vehicleId,
                                         @NotNull Long issuedByUserId) {}
