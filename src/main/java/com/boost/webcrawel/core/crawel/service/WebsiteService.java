package com.boost.webcrawel.core.crawel.service;

import com.boost.webcrawel.core.crawel.entity.Website;

public interface WebsiteService {

    Website getByUrl(String link);

    void validateWebsiteDuplication(String link);

    Website createNewWebsiteInCaseOfNotExisted(String link);

    Website getById(Long id);
}
