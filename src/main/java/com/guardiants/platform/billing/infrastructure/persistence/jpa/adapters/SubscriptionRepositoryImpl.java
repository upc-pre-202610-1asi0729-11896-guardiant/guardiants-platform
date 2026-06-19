package com.guardiants.platform.billing.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.billing.domain.model.aggregates.Subscription;
import com.guardiants.platform.billing.domain.repositories.SubscriptionRepository;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.assemblers.SubscriptionEntityAssembler;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.repositories.SubscriptionPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

    private final SubscriptionPersistenceRepository persistenceRepository;
    private final SubscriptionEntityAssembler assembler;

    public SubscriptionRepositoryImpl(SubscriptionPersistenceRepository persistenceRepository,
                                      SubscriptionEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public Optional<Subscription> findByOwnerId(Long ownerId) {
        return persistenceRepository.findByOwnerId(ownerId)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public Optional<Subscription> findByStripeSubscriptionId(String stripeId) {
        return persistenceRepository.findByStripeSubscriptionId(stripeId)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public Subscription save(Subscription subscription) {
        return assembler.toDomainFromPersistenceEntity(persistenceRepository.save(
                assembler.toPersistenceEntityFromDomain(subscription)));
    }
}