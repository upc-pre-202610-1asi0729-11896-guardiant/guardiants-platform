package com.guardiants.platform.commands.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record ReportTheftResource(@NotNull Long vehicleId,
                                  @NotNull Long reportedByUserId) {}
