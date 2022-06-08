package com.boost.webcrawel.stream;

import com.boost.webcrawel.core.crawel.service.WebPageService;
import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private final static Logger logger = LoggerFactory.getLogger(KafkaListeners.class);

    private final WebPageService webPageService;

    @Autowired
    public KafkaListeners(WebPageService webPageService) {
        this.webPageService = webPageService;
    }


    @KafkaListener(topics = "${web.craweler.kafka.topicName}", groupId = "${web.craweler.kafka.consumer.groupId}")
    public void listen(CrawelingEvent crawelingEvent) {
        logger.info().message("crawel web page")
                .field("link", crawelingEvent.getLink())
                .field("depth", crawelingEvent.getCrawelingDepth()).log();
        webPageService.crawelWebPage(crawelingEvent);
    }
}
