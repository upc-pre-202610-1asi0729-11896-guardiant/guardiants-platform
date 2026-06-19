package com.guardiants.platform.billing.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.billing.domain.model.aggregates.Payment;
import com.guardiants.platform.billing.domain.repositories.PaymentRepository;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.assemblers.PaymentEntityAssembler;
import com.guardiants.platform.billing.infrastructure.persistence.jpa.repositories.PaymentPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentPersistenceRepository persistenceRepository;
    private final PaymentEntityAssembler assembler;

    public PaymentRepositoryImpl(PaymentPersistenceRepository persistenceRepository,
                                 PaymentEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public List<Payment> findAllBySubscriptionId(Long subscriptionId) {
        return persistenceRepository.findAllBySubscriptionId(subscriptionId)
                .stream().map(assembler::toDomainFromPersistenceEntity).toList();
    }

    @Override
    public Optional<Payment> findByStripePaymentIntentId(String stripePaymentIntentId) {
        return persistenceRepository.findByStripePaymentIntentId(stripePaymentIntentId)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public Payment save(Payment payment) {
        return assembler.toDomainFromPersistenceEntity(persistenceRepository.save(
                assembler.toPersistenceEntityFromDomain(payment)));
    }
}