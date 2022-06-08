package com.boost.webcrawel.controller.v1;

import com.boost.webcrawel.core.commons.Constants;
import com.boost.webcrawel.core.commons.dto.GenericResponseDTO;
import com.boost.webcrawel.core.crawel.dto.request.WebCrawelRequest;
import com.boost.webcrawel.core.crawel.dto.response.WebsiteResponseDto;
import com.boost.webcrawel.core.crawel.service.CrawelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping(Constants.API_BASE_URL + "/web-crawel")
public class WebCrawelContoller {

    @Autowired
    private CrawelService crawelService;

    @GetMapping
    public ResponseEntity<WebsiteResponseDto> getWebLinks(@RequestParam("url") String url,
                                                          @RequestParam(required = false, defaultValue = "1") Integer page,
                                                          @RequestParam(required = false, defaultValue = "10") Integer size) {
        return new ResponseEntity<>(crawelService.getWebsitePages(url, page, size), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GenericResponseDTO> crawelLink(@RequestBody @Valid WebCrawelRequest webCrawelRequest) throws IOException {
        crawelService.crawelLink(webCrawelRequest.getLink());
        return new ResponseEntity<>(new GenericResponseDTO("Your link is being craweling now"), HttpStatus.OK);
    }
}
