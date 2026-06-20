package com.guardiants.platform.fleet.application.queryservices;

import com.guardiants.platform.fleet.domain.model.aggregates.Fleet;
import com.guardiants.platform.fleet.domain.model.queries.GetAllFleetsByOwnerIdQuery;
import com.guardiants.platform.fleet.domain.model.queries.GetFleetByIdQuery;

import java.util.List;
import java.util.Optional;

public interface FleetQueryService {
    Optional<Fleet> handle(GetFleetByIdQuery query);
    List<Fleet> handle(GetAllFleetsByOwnerIdQuery query);
}
