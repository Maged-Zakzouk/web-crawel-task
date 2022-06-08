package com.boost.webcrawel.core.crawel.service;

import com.boost.webcrawel.core.crawel.dto.response.WebsiteResponseDto;

public interface CrawelService {

    WebsiteResponseDto getWebsitePages(String link, Integer page, Integer size);

    void crawelLink(String link);
}
