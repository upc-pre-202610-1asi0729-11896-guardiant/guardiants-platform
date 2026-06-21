package com.guardiants.platform.query.application.internal.outboundservices.export;

public interface ReportExportPort {
    String exportToPdf(Object view, Long exportId);
    String exportToCsv(Object view, Long exportId);
}
