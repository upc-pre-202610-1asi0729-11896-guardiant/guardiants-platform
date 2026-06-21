package com.guardiants.platform.query.infrastructure.persistence.jpa.repositories;

import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.SearchIndexPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SearchIndexPersistenceRepository
        extends JpaRepository<SearchIndexPersistenceEntity, Long> {
    List<SearchIndexPersistenceEntity> findByEntityTypeAndLabelContainingIgnoreCase(
            String entityType, String queryText);
}
