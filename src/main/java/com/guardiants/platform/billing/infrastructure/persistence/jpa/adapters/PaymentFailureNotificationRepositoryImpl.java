package com.guardiants.platform.billing.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.billing.domain.model.aggregates.PaymentFailureNotification;
import com.guardiants.platform.billing.domain.repositories.PaymentFailureNotificationRepository;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.assemblers.PaymentFailureNotificationEntityAssembler;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.repositories.PaymentFailureNotificationPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class PaymentFailureNotificationRepositoryImpl
        implements PaymentFailureNotificationRepository {

    private final PaymentFailureNotificationPersistenceRepository persistenceRepository;
    private final PaymentFailureNotificationEntityAssembler assembler;

    public PaymentFailureNotificationRepositoryImpl(
            PaymentFailureNotificationPersistenceRepository persistenceRepository,
            PaymentFailureNotificationEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<PaymentFailureNotification> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public Optional<PaymentFailureNotification> findByOwnerId(Long ownerId) {
        return persistenceRepository.findByOwnerId(ownerId)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public PaymentFailureNotification save(PaymentFailureNotification notification) {
        return assembler.toDomainFromPersistenceEntity(persistenceRepository.save(
                assembler.toPersistenceEntityFromDomain(notification)));
    }
}
