package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.telemetry.domain.model.aggregates.TelemetryPoint;
import com.guardiants.platform.telemetry.domain.repositories.TelemetryPointRepository;
import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.assemblers.TelemetryPointEntityAssembler;
import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.repositories.TelemetryPointPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TelemetryPointRepositoryImpl implements TelemetryPointRepository {

    private final TelemetryPointPersistenceRepository persistenceRepository;
    private final TelemetryPointEntityAssembler assembler;

    public TelemetryPointRepositoryImpl(TelemetryPointPersistenceRepository persistenceRepository,
                                        TelemetryPointEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<TelemetryPoint> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public TelemetryPoint save(TelemetryPoint point) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(point)));
    }
}
