package com.guardiants.platform.query.infrastructure.persistence.jpa.adapters;

import com.guardiants.platform.query.domain.model.readmodels.SearchEntityType;
import com.guardiants.platform.query.domain.model.readmodels.SearchResultView;
import com.guardiants.platform.query.domain.repositories.OperationalReportSearchIndexRepository;
import com.guardiants.platform.query.infrastructure.persistence.jpa.assemblers.SearchIndexEntityAssembler;
import com.guardiants.platform.query.infrastructure.persistence.jpa.repositories.SearchIndexPersistenceRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;

@Repository
public class OperationalReportSearchIndexRepositoryImpl implements OperationalReportSearchIndexRepository {

    private final SearchIndexPersistenceRepository persistenceRepository;
    private final SearchIndexEntityAssembler assembler;

    public OperationalReportSearchIndexRepositoryImpl(SearchIndexPersistenceRepository persistenceRepository,
                                                      SearchIndexEntityAssembler assembler) {
        this.persistenceRepository = persistenceRepository;
        this.assembler = assembler;
    }

    @Override
    public List<SearchResultView> search(String queryText) {
        return persistenceRepository
                .findByEntityTypeAndLabelContainingIgnoreCase(SearchEntityType.OPERATIONAL_REPORT.name(), queryText)
                .stream().map(assembler::toViewFromPersistenceEntity).toList();
    }

    @Override
    public void upsert(SearchResultView view) {
        var entity = assembler.toPersistenceEntityFromView(view);
        entity.setEntityType(SearchEntityType.OPERATIONAL_REPORT.name());
        entity.setIndexedAt(Instant.now());
        persistenceRepository.save(entity);
    }
}
