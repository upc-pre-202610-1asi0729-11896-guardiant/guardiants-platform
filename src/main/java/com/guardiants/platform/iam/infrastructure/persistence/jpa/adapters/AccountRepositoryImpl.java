package com.guardiants.platform.iam.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.iam.domain.model.aggregates.Account;
import com.guardiants.platform.iam.domain.repositories.AccountRepository;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.assemblers.AccountEntityAssembler;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.repositories.AccountPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class AccountRepositoryImpl implements AccountRepository {

    private final AccountPersistenceRepository persistenceRepository;
    private final AccountEntityAssembler assembler;

    public AccountRepositoryImpl(AccountPersistenceRepository persistenceRepository,
                                 AccountEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return persistenceRepository.findByEmail(email)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return persistenceRepository.existsByEmail(email);
    }

    @Override
    public Account save(Account account) {
        var entity = assembler.toPersistenceEntityFromDomain(account);
        var saved = persistenceRepository.save(entity);
        return assembler.toDomainFromPersistenceEntity(saved);
    }
}