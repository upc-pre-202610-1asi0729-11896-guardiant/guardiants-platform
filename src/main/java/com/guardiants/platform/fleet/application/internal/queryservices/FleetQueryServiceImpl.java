package com.guardiants.platform.fleet.application.internal.queryservices;

import com.guardiants.platform.fleet.application.queryservices.FleetQueryService;
import com.guardiants.platform.fleet.domain.model.aggregates.Fleet;
import com.guardiants.platform.fleet.domain.model.queries.GetAllFleetsByOwnerIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetFleetByIdQuery;
import com.guardiants.platform.fleet.domain.repositories.FleetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FleetQueryServiceImpl implements FleetQueryService {

    private final FleetRepository fleetRepository;

    public FleetQueryServiceImpl(FleetRepository fleetRepository) {
        this.fleetRepository = fleetRepository;
    }

    @Override
    public Optional<Fleet> handle(GetFleetByIdQuery query) {
        return fleetRepository.findById(query.fleetId());
    }

    @Override
    public List<Fleet> handle(GetAllFleetsByOwnerIdQuery query) {
        return fleetRepository.findAllByOwnerId(query.ownerId());
    }
}
