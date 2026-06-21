package com.guardiants.platform.query.infrastructure.persistence.jpa.assemblers;

import com.guardiants.platform.query.domain.model.readmodels.SearchEntityType;
import com.guardiants.platform.query.domain.model.readmodels.SearchResultView;
import com.guardiants.platform.query.infrastructure.persistence.jpa.entities.SearchIndexPersistenceEntity;
import org.springframework.stereotype.Component;

@Component
public class SearchIndexEntityAssembler {

    public SearchIndexPersistenceEntity toPersistenceEntityFromView(SearchResultView view) {
        var entity = new SearchIndexPersistenceEntity();
        entity.setEntityType(view.getEntityType() != null ? view.getEntityType().name() : null);
        entity.setEntityId(view.getEntityId());
        entity.setLabel(view.getLabel());
        entity.setSubtitle(view.getSubtitle());
        entity.setLat(view.getLat());
        entity.setLng(view.getLng());
        entity.setOccurredAt(view.getOccurredAt());
        return entity;
    }

    public SearchResultView toViewFromPersistenceEntity(SearchIndexPersistenceEntity entity) {
        var view = new SearchResultView();
        view.setEntityType(entity.getEntityType() != null
                ? SearchEntityType.valueOf(entity.getEntityType()) : null);
        view.setEntityId(entity.getEntityId());
        view.setLabel(entity.getLabel());
        view.setSubtitle(entity.getSubtitle());
        view.setLat(entity.getLat());
        view.setLng(entity.getLng());
        view.setOccurredAt(entity.getOccurredAt());
        return view;
    }
}
