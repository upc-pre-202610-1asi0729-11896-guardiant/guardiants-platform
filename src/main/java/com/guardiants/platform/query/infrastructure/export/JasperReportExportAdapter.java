package com.guardiants.platform.query.infrastructure.export;

import com.guardiants.platform.query.application.internal.outboundservices.export.ReportExportPort;
import org.springframework.stereotype.Component;

/**
 * Report export adapter. In dev it returns a placeholder download URL; in production it would
 * render the report with JasperReports and upload it to object storage.
 */
@Component
public class JasperReportExportAdapter implements ReportExportPort {

    @Override
    public String exportToPdf(Object view, Long exportId) {
        // TODO: integrate JasperReports
        return "/exports/" + exportId + ".pdf";
    }

    @Override
    public String exportToCsv(Object view, Long exportId) {
        return "/exports/" + exportId + ".csv";
    }
}
