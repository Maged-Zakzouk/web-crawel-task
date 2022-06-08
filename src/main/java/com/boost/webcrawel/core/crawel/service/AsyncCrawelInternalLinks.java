package com.boost.webcrawel.core.crawel.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AsyncCrawelInternalLinks {

    private final CrawelService crawelService;

    public AsyncCrawelInternalLinks(@Lazy CrawelService crawelService) {
        this.crawelService = crawelService;
    }

    @Async("bulkProductsPricesUpdateThreadPoolTaskExecutor")
    public void crawelInternalLinks(List<String> links) {
        links.forEach(link -> {
            crawelService.crawelLink(link);
        });
    }
}
