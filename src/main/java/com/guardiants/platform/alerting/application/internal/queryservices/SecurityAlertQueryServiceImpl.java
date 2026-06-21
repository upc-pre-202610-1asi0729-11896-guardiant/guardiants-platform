package com.guardiants.platform.alerting.application.internal.queryservices;

import com.guardiants.platform.alerting.application.queryservices.SecurityAlertQueryService;
import com.guardiants.platform.alerting.domain.model.aggregates.SecurityAlert;
import com.guardiants.platform.alerting.domain.model.queries.GetSecurityAlertsByOwnerIdQuery;
import com.guardiants.platform.alerting.domain.repositories.SecurityAlertRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SecurityAlertQueryServiceImpl implements SecurityAlertQueryService {

    private final SecurityAlertRepository securityAlertRepository;

    public SecurityAlertQueryServiceImpl(SecurityAlertRepository securityAlertRepository) {
        this.securityAlertRepository = securityAlertRepository;
    }

    @Override
    public List<SecurityAlert> handle(GetSecurityAlertsByOwnerIdQuery query) {
        var all = securityAlertRepository.findAllByOwnerId(query.ownerId());
        var filtered = applyPeriodFilter(all, query);
        return applyCategoryFilter(filtered, query);
    }

    private List<SecurityAlert> applyPeriodFilter(List<SecurityAlert> alerts,
                                                  GetSecurityAlertsByOwnerIdQuery query) {
        if (query.period() == null) return alerts;
        Instant cutoff = switch (query.period()) {
            case LAST_HOUR -> Instant.now().minus(1, ChronoUnit.HOURS);
            case TODAY -> Instant.now().truncatedTo(ChronoUnit.DAYS);
            case LAST_7_DAYS -> Instant.now().minus(7, ChronoUnit.DAYS);
            case LAST_30_DAYS -> Instant.now().minus(30, ChronoUnit.DAYS);
        };
        return alerts.stream()
                .filter(a -> a.getGeneratedAt().isAfter(cutoff))
                .toList();
    }

    private List<SecurityAlert> applyCategoryFilter(List<SecurityAlert> alerts,
                                                    GetSecurityAlertsByOwnerIdQuery query) {
        if (query.category() == null) return alerts;
        return switch (query.category()) {
            case ALL -> alerts;
            case UNREAD -> alerts.stream().filter(SecurityAlert::isUnread).toList();
            case URGENT -> alerts.stream().filter(SecurityAlert::isUrgent).toList();
        };
    }
}
