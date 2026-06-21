package com.guardiants.platform.query.interfaces.rest.resources;

import java.time.Instant;

public record ReportExportResource(
        Long id, Long sourceReportId, String format,
        Instant generatedAt, String downloadUrl) {}
