package com.boost.webcrawel.core.crawel.service;


import com.boost.webcrawel.core.commons.dto.ListResponseWrapper;
import com.boost.webcrawel.core.crawel.dto.response.PageResponseDto;
import com.boost.webcrawel.stream.CrawelingEvent;
import org.springframework.data.domain.Pageable;

public interface WebPageService {

    void crawelWebPage(CrawelingEvent crawelingEvent);

    ListResponseWrapper<PageResponseDto> getPaginatedWebPagesByWebsiteId(Long websiteId, Pageable pageable);
}
