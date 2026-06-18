package com.guardiants.platform.iam.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.iam.domain.model.aggregates.Session;
import com.guardiants.platform.iam.domain.model.valueobjects.SessionStatus;
import com.guardiants.platform.iam.domain.repositories.SessionRepository;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.assemblers.SessionEntityAssembler;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.repositories.SessionPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class SessionRepositoryImpl implements SessionRepository {

    private final SessionPersistenceRepository persistenceRepository;
    private final SessionEntityAssembler assembler;

    public SessionRepositoryImpl(SessionPersistenceRepository persistenceRepository,
                                 SessionEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<Session> findById(Long id) {
        return persistenceRepository.findById(id)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public Optional<Session> findByRefreshToken(String refreshToken) {
        return persistenceRepository.findByRefreshToken(refreshToken)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public Optional<Session> findActiveByUserId(Long userId) {
        return persistenceRepository
                .findFirstByUserIdAndStatus(userId, SessionStatus.ACTIVE.name())
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public Session save(Session session) {
        var entity = assembler.toPersistenceEntityFromDomain(session);
        var saved = persistenceRepository.save(entity);
        return assembler.toDomainFromPersistenceEntity(saved);
    }
}