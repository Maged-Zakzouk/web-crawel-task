package com.boost.webcrawel.core.crawel.dto.response;

import com.boost.webcrawel.core.commons.dto.ListResponseWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebsiteResponseDto {

    private String url;

    private ListResponseWrapper<PageResponseDto> pages;
}
