package com.guardiants.platform.iam.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.iam.domain.repositories.SessionRepository;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.assemblers.SessionEntityAssembler;
import com.guardiants.platform.iam.infrastructure.persistence.jpa.repositories.SessionPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/*
*  Optional<Session> findById(Long id);
    Optional<Session> findByRefreshToken(String refreshToken);
    Optional<Session> findActiveByUserId(Long userId);
    Session save(Session session);*/

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
        return persistenceRepository.findFirstByUserIdAndStatus(Long userId);
    }
/*
* findActiveByUserId(Long userId);*/
    @Override
    public Session save(Session Session) {
        var entity = assembler.toPersistenceEntityFromDomain(Session);
        var saved = persistenceRepository.save(entity);
        return assembler.toDomainFromPersistenceEntity(saved);
    }
}