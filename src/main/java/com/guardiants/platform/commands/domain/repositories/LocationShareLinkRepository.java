package com.guardiants.platform.commands.domain.repositories;

import com.guardiants.platform.commands.domain.model.aggregates.LocationShareLink;
import java.util.Optional;

public interface LocationShareLinkRepository {
    Optional<LocationShareLink> findById(Long id);
    LocationShareLink save(LocationShareLink link);
}
