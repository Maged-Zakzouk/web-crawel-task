package com.boost.webcrawel.core.crawel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CraweledWebPageDto {

    private String title;

    private String hostUrl;

    private List<String> links = new ArrayList<>();

}
