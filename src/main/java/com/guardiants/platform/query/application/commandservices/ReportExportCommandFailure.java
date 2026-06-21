package com.guardiants.platform.query.application.commandservices;

public interface ReportExportCommandFailure {
    String messageKey();

    record SourceNotFound() implements ReportExportCommandFailure {
        public String messageKey() { return "query.error.exportSourceNotFound"; }
    }

    record UnsupportedFormat() implements ReportExportCommandFailure {
        public String messageKey() { return "query.error.unsupportedExportFormat"; }
    }
}
