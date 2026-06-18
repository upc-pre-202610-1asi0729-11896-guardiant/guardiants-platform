package com.guardiants.platform.iam.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.iam.domain.model.aggregates.User;
import com.guardiants.platform.iam.domain.repositories.UserRepository;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.assemblers.UserEntityAssembler;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.repositories.UserPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserPersistenceRepository persistenceRepository;
    private final UserEntityAssembler assembler;

    public UserRepositoryImpl(UserPersistenceRepository persistenceRepository,
                                 UserEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<User> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return persistenceRepository.findByEmail(email)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return persistenceRepository.existsByEmail(email);
    }

    @Override
    public User save(User User) {
        var entity = assembler.toPersistenceEntityFromDomain(User);
        var saved = persistenceRepository.save(entity);
        return assembler.toDomainFromPersistenceEntity(saved);
    }
}