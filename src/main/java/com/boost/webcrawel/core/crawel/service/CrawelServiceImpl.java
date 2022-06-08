package com.boost.webcrawel.core.crawel.service;

import com.boost.webcrawel.core.commons.dto.ListResponseWrapper;
import com.boost.webcrawel.core.commons.exception.LogicalException;
import com.boost.webcrawel.core.crawel.dto.response.PageResponseDto;
import com.boost.webcrawel.core.crawel.dto.response.WebsiteResponseDto;
import com.boost.webcrawel.core.crawel.entity.Status;
import com.boost.webcrawel.core.crawel.entity.Website;
import com.boost.webcrawel.stream.CrawelingEvent;
import com.boost.webcrawel.utils.CrawelingUtils;
import com.boost.webcrawel.utils.JpaPagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.boost.webcrawel.core.commons.exception.ServerError.WEBSITE_UNDER_CRAWLING;

@Service
public class CrawelServiceImpl implements CrawelService {

    private final WebsiteService websiteService;
    private final WebPageService webPageService;
    private final KafkaTemplate<String, CrawelingEvent> kafkaTemplate;
    private final String topicName;
    private final int crawelingDepth;

    @Autowired
    public CrawelServiceImpl(WebsiteService websiteService,
                             WebPageService webPageService,
                             KafkaTemplate<String, CrawelingEvent> kafkaTemplate,
                             @Value(value = "${web.craweler.kafka.topicName}") String topicName,
                             @Value(value = "${web.craweler.crawelingDepth}") int crawelingDepth) {
        this.websiteService = websiteService;
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
        this.crawelingDepth = crawelingDepth;
        this.webPageService = webPageService;
    }

    @Override
    public WebsiteResponseDto getWebsitePages(String link, Integer page, Integer size) {
        CrawelingUtils.validateURL(link);
        Website website = websiteService.getByUrl(link);
        if (Status.PENDING.equals(website.getStatus())) {
            throw new LogicalException(WEBSITE_UNDER_CRAWLING);
        }
        Pageable pageable = JpaPagingUtil.getPage(page, size);
        ListResponseWrapper<PageResponseDto> pages = webPageService.getPaginatedWebPagesByWebsiteId(website.getId(), pageable);
        return new WebsiteResponseDto(website.getUrl(), pages);
    }

    @Override
    public void crawelLink(String link) {
        CrawelingUtils.validateURL(link);
        websiteService.validateWebsiteDuplication(link);
        kafkaTemplate.send(topicName, new CrawelingEvent(link, crawelingDepth));
    }
}
