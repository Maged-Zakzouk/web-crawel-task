package com.boost.webcrawel.core.crawel.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDto {

    String title;

    String url;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String hostUrl;

    Boolean isCrawelledLink;

    List<String> links = new ArrayList<>();

    public PageResponseDto(String title, String url, Boolean isCrawelledLink, List<String> links) {
        this.title = title;
        this.url = url;
        this.links = links;
        this.isCrawelledLink = isCrawelledLink;
    }
}
