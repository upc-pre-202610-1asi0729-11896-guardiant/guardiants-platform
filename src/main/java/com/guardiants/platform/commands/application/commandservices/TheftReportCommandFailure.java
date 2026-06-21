package com.guardiants.platform.commands.application.commandservices;

public interface TheftReportCommandFailure {
    String messageKey();

    record TheftReportNotFound() implements TheftReportCommandFailure {
        public String messageKey() { return "commands.error.theftReportNotFound"; }
    }
}
