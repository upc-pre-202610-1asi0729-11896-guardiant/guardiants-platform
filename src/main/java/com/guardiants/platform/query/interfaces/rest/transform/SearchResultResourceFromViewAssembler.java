package com.guardiants.platform.query.interfaces.rest.transform;

import com.guardiants.platform.query.domain.model.readmodels.SearchResultView;
import com.guardiants.platform.query.interfaces.rest.resources.SearchResultResource;

public class SearchResultResourceFromViewAssembler {

    public static SearchResultResource toResourceFromView(SearchResultView view) {
        return new SearchResultResource(
                view.getEntityType() != null ? view.getEntityType().name() : null,
                view.getEntityId(),
                view.getLabel(), view.getSubtitle(),
                view.getLat(), view.getLng(), view.getOccurredAt());
    }
}
