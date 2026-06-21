package com.guardiants.platform.billing.application.internal.eventhandlers;

import com.guardiants.platform.billing.domain.model.aggregates.Plan;
import com.guardiants.platform.billing.domain.repositories.PlanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component("billingApplicationReadyEventHandler")
public class ApplicationReadyEventHandler {

    private final PlanRepository planRepository;

    public ApplicationReadyEventHandler(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        seedPlans();
    }

    private void seedPlans() {
        if (!planRepository.findAll().isEmpty()) {
            log.debug("Plans already seeded — skipping.");
            return;
        }
        planRepository.save(new Plan("free", "Free", 0.0, 1, 1,
                "[\"1 vehicle\",\"Basic tracking\",\"Email alerts\"]"));
        planRepository.save(new Plan("starter", "Starter", 9.99, 1, 5,
                "[\"5 vehicles\",\"Real-time tracking\",\"Alerts\",\"Route history\"]"));
        planRepository.save(new Plan("pro", "Pro", 29.99, 1, 20,
                "[\"20 vehicles\",\"All features\",\"Driving reports\",\"Operational reports\"]"));
        planRepository.save(new Plan("enterprise", "Enterprise", 99.99, 1, 999,
                "[\"Unlimited vehicles\",\"All features\",\"Priority support\",\"Custom integrations\"]"));
        log.info("Billing plans seeded: free, starter, pro, enterprise.");
    }
}