package com.guardiants.platform.query.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.query.domain.model.readmodels.RouteHistoryView;
import com.guardiants.platform.query.domain.repositories.RouteHistoryViewRepository;
import com.guardiants.platform.query.infrastructure.persistence.jpa.assemblers.RouteHistoryViewEntityAssembler;
import com.guardiants.platform.query.infrastructure.persistence.jpa.repositories.RouteHistoryViewPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public class RouteHistoryViewRepositoryImpl implements RouteHistoryViewRepository {

    private final RouteHistoryViewPersistenceRepository persistenceRepository;
    private final RouteHistoryViewEntityAssembler assembler;

    public RouteHistoryViewRepositoryImpl(RouteHistoryViewPersistenceRepository persistenceRepository,
                                          RouteHistoryViewEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<RouteHistoryView> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toViewFromPersistenceEntity);
    }

    @Override
    public Optional<RouteHistoryView> findByVehicleIdAndPeriod(Long vehicleId, Instant start, Instant end) {
        return persistenceRepository
                .findByVehicleIdAndPeriodStartGreaterThanEqualAndPeriodEndLessThanEqual(vehicleId, start, end)
                .map(assembler::toViewFromPersistenceEntity);
    }

    @Override
    public RouteHistoryView save(RouteHistoryView view) {
        return assembler.toViewFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromView(view)));
    }
}
