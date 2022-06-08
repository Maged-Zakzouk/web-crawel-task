package com.boost.webcrawel.core.crawel.service;

import com.boost.webcrawel.core.commons.dto.ListResponseWrapper;
import com.boost.webcrawel.core.crawel.dto.SourceDestinationLinkingDto;
import com.boost.webcrawel.core.crawel.dto.response.PageResponseDto;
import com.boost.webcrawel.core.crawel.entity.Status;
import com.boost.webcrawel.core.crawel.entity.WebPage;
import com.boost.webcrawel.core.crawel.entity.WebPageLink;
import com.boost.webcrawel.core.crawel.entity.Website;
import com.boost.webcrawel.core.crawel.repo.WebPageLinkRepo;
import com.boost.webcrawel.core.crawel.repo.WebPageRepo;
import com.boost.webcrawel.core.crawel.repo.WebsiteRepo;
import com.boost.webcrawel.stream.CrawelingEvent;
import com.boost.webcrawel.utils.CrawelingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WebPageServiceImpl implements WebPageService {

    private final WebsiteService websiteService;
    private final WebPageRepo webPageRepo;
    private final WebPageLinkRepo webPageLinkRepo;
    private final WebsiteRepo websiteRepo;
    private final KafkaTemplate<String, CrawelingEvent> kafkaTemplate;
    private final String topicName;
    private final AsyncCrawelInternalLinks asyncCrawelInternalLinks;

    @Autowired
    public WebPageServiceImpl(WebsiteService websiteService,
                              WebPageRepo webPageRepo,
                              WebPageLinkRepo webPageLinkRepo,
                              WebsiteRepo websiteRepo,
                              KafkaTemplate<String, CrawelingEvent> kafkaTemplate,
                              @Value(value = "${web.craweler.kafka.topicName}") String topicName,
                              AsyncCrawelInternalLinks asyncCrawelInternalLinks) {
        this.websiteService = websiteService;
        this.webPageRepo = webPageRepo;
        this.webPageLinkRepo = webPageLinkRepo;
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
        this.asyncCrawelInternalLinks = asyncCrawelInternalLinks;
        this.websiteRepo = websiteRepo;
    }

    @Override
    public void crawelWebPage(CrawelingEvent crawelingEvent) {
        if (webPageRepo.existsByUrl(crawelingEvent.getLink())) {
            crawelExistedWebPage(crawelingEvent);
        } else {
            crawelNewWebPage(crawelingEvent);
        }
    }

    @Override
    public ListResponseWrapper<PageResponseDto> getPaginatedWebPagesByWebsiteId(Long websiteId, Pageable pageable) {
        Page<WebPage> webPagesPage = webPageRepo.getWebPagesByWebsiteId(websiteId, pageable);
        List<PageResponseDto> pages = new ArrayList<>();
        if (!webPagesPage.getContent().isEmpty()) {
            Map<Long, List<String>> webPageLinksMap = constructWebPageLinksMap(webPagesPage);
            webPagesPage.getContent().forEach(webPage -> {
                List<String> links = Objects.isNull(webPageLinksMap.get(webPage.getId())) ? new ArrayList<>() :
                        webPageLinksMap.get(webPage.getId());
                pages.add(new PageResponseDto(webPage.getTitle(), webPage.getUrl(), webPage.isActive(), links));
            });
        }
        return new ListResponseWrapper<>(pages, webPagesPage.getTotalPages(), webPagesPage.getTotalElements());
    }

    private void crawelExistedWebPage(CrawelingEvent crawelingEvent) {
        WebPage webPage = webPageRepo.findByUrl(crawelingEvent.getLink());
        if (Status.ACTIVE.equals(webPage.getStatus())) {
            return;
        }
        Website website = websiteService.getById(webPage.getWebsite().getId());
        if (crawelingEvent.getCrawelingDepth() == 0) {
            webPage.setStatus(Status.IGNORED);
            webPageRepo.saveAndFlush(webPage);
            activateWebsiteIfHasNoPendingWebPages(website);
            return;
        }
        PageResponseDto pageResponseDto = CrawelingUtils.crawelWebPage(crawelingEvent.getLink());
        webPage.setTitle(pageResponseDto.getTitle());
        webPage.setStatus(Status.ACTIVE);
        webPage = webPageRepo.saveAndFlush(webPage);
        saveWebPageWithLinksWithoutDuplication(pageResponseDto, website, webPage, crawelingEvent.getCrawelingDepth());
        activateWebsiteIfHasNoPendingWebPages(website);
    }

    private void crawelNewWebPage(CrawelingEvent crawelingEvent) {
        PageResponseDto pageResponseDto = CrawelingUtils.crawelWebPage(crawelingEvent.getLink());
        Website website = websiteService.createNewWebsiteInCaseOfNotExisted(pageResponseDto.getHostUrl());
        WebPage webPage = new WebPage();
        webPage.setTitle(pageResponseDto.getTitle());
        webPage.setUrl(CrawelingUtils.constructUrl(pageResponseDto.getUrl()));
        webPage.setWebsite(website);
        webPage.setStatus(Status.ACTIVE);
        webPage = webPageRepo.saveAndFlush(webPage);
        saveWebPageWithLinksWithoutDuplication(pageResponseDto, website, webPage, crawelingEvent.getCrawelingDepth());
        activateWebsiteIfHasNoPendingWebPages(website);
    }

    private void saveWebPageWithLinksWithoutDuplication(PageResponseDto pageResponseDto, Website website, WebPage webPage, int crawelingDepth) {
        List<WebPage> linkedPages = saveWebPagesWithoutDuplication(pageResponseDto, website);
        saveWebPageLinks(webPage, linkedPages);
        if (crawelingDepth > 0) {
            linkedPages.forEach(linkedPage -> kafkaTemplate.send(topicName, new CrawelingEvent(linkedPage.getUrl(), crawelingDepth - 1)));
        }
    }

    private List<WebPage> saveWebPagesWithoutDuplication(PageResponseDto pageResponseDto, Website website) {
        List<String> links = pageResponseDto.getLinks();
        List<WebPage> webPages = webPageRepo.findAllByWebsiteId(website.getId());
        Map<String, WebPage> webPagesMap = webPages.stream().collect(Collectors.toMap(WebPage::getUrl, wp -> wp));
        Set<String> webPagesUrls = webPages.stream().map(WebPage::getUrl).collect(Collectors.toSet());

        List<WebPage> linkedPages = new ArrayList<>();
        for (String currentLink : links) {
            if (!webPagesUrls.contains(currentLink)) {
                linkedPages.add(new WebPage(currentLink, website));
            } else {
                linkedPages.add(webPagesMap.get(currentLink));
            }
        }
        return webPageRepo.saveAllAndFlush(linkedPages);
    }

    private void saveWebPageLinks(WebPage webPage, List<WebPage> linkedPages) {
        List<WebPageLink> webPageLinks = new ArrayList<>();
        for (WebPage linkedPage : linkedPages) {
            webPageLinks.add(new WebPageLink(webPage, linkedPage));
        }
        webPageLinkRepo.saveAllAndFlush(webPageLinks);
    }

    private void activateWebsiteIfHasNoPendingWebPages(Website website) {
        Boolean hasPendingWebPage = webPageRepo.hasWebPageWithPendingStatusByWebsiteId(website.getId());
        if (!hasPendingWebPage) {
            website.setStatus(Status.ACTIVE);
            websiteRepo.saveAndFlush(website);
        }
    }

    private Map<Long, List<String>> constructWebPageLinksMap(Page<WebPage> webPagesPage) {
        List<Long> pagesIds = webPagesPage.getContent().stream().map(WebPage::getId).collect(Collectors.toList());
        List<SourceDestinationLinkingDto> sourceDestinationLinkingDtos = webPageLinkRepo.findInSourceIds(pagesIds);
        Map<Long, List<String>> webPageLinksMap = new HashMap<>();
        sourceDestinationLinkingDtos.forEach(sourceDestinationLinkingDto -> {
            Long sourceId = sourceDestinationLinkingDto.getId();
            if (!webPageLinksMap.containsKey(sourceId)) {
                webPageLinksMap.put(sourceId, new ArrayList<>());
            }
            webPageLinksMap.get(sourceId).add(sourceDestinationLinkingDto.getUrl());
        });
        return webPageLinksMap;
    }
}
