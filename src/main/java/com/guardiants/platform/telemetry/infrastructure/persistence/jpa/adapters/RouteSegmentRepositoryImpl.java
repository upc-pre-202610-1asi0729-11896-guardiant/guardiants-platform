package com.guardiants.platform.telemetry.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.telemetry.domain.model.aggregates.RouteSegment;
import com.guardiants.platform.telemetry.domain.repositories.RouteSegmentRepository;
import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.assemblers.RouteSegmentEntityAssembler;
import com.guardiants.platform.telemetry.infrastructure.persistence.jpa.repositories.RouteSegmentPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class RouteSegmentRepositoryImpl implements RouteSegmentRepository {

    private final RouteSegmentPersistenceRepository persistenceRepository;
    private final RouteSegmentEntityAssembler assembler;

    public RouteSegmentRepositoryImpl(RouteSegmentPersistenceRepository persistenceRepository,
                                      RouteSegmentEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public Optional<RouteSegment> findById(Long id) {
        return persistenceRepository.findById(id).map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public Optional<RouteSegment> findOngoingByVehicleId(Long vehicleId) {
        return persistenceRepository.findFirstByVehicleIdAndEndedAtIsNull(vehicleId)
                .map(assembler::toDomainFromPersistenceEntity);
    }

    @Override
    public List<RouteSegment> findAllByVehicleIdAndPeriod(Long vehicleId, Instant start, Instant end) {
        return persistenceRepository.findAllByVehicleIdAndStartedAtBetween(vehicleId, start, end)
                .stream().map(assembler::toDomainFromPersistenceEntity).toList();
    }

    @Override
    public RouteSegment save(RouteSegment segment) {
        return assembler.toDomainFromPersistenceEntity(
                persistenceRepository.save(assembler.toPersistenceEntityFromDomain(segment)));
    }
}
