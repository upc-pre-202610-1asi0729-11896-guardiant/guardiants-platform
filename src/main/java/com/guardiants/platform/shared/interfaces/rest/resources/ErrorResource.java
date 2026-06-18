package com.guardiants.platform.shared.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jspecify.annotations.Nullable;

/**
 * Standard error response resource returned from REST endpoints.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResource(String code, String message, @Nullable String details) {

    /** Creates an ErrorResource with code and message only. */
    public ErrorResource(String code, String message) {
        this(code, message, null);
    }
}