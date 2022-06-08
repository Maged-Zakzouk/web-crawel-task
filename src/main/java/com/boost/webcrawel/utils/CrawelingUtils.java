package com.boost.webcrawel.utils;


import com.boost.webcrawel.core.commons.exception.LogicalException;
import com.boost.webcrawel.core.crawel.dto.response.PageResponseDto;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.regex.Pattern;

import static com.boost.webcrawel.core.commons.exception.ServerError.FAILED_TO_CRAWEL_THIS_WEBSITE;
import static com.boost.webcrawel.core.commons.exception.ServerError.INVALID_URL;

public class CrawelingUtils {

    public static void validateURL(String link) {
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(link)) {
            throw new LogicalException(INVALID_URL);
        }
    }

    public static Boolean isValidInternalLink(String url, String baseUrl) {
        final Pattern FILTER = Pattern.compile(".*(\\.(css|js|gif|jpg|jpeg|png|mp3|mp4|zip|gz|pdf|xls|xlsx|doc|docx|CSS|JS|GIF|JPG|JPEG|PNG|MP3|MP4|ZIP|GZ|PDF|XLS|XLSX|DOC|DOCX))$");
        return url.startsWith(baseUrl) && !url.contains("%") && !FILTER.matcher(url).matches();
    }

    public static String getHostUrl(String link) {
        try {
            URL url = new URL(link);
            return url.getProtocol() + "://" + url.getHost();
        } catch (MalformedURLException e) {
            throw new LogicalException(INVALID_URL);
        }
    }

    public static PageResponseDto crawelWebPage(String link) {
        try {
            final Document doc = Jsoup.connect(link).get();
            Elements elements = doc.select("a");

            PageResponseDto pageResponseDto = new PageResponseDto();
            pageResponseDto.setTitle(doc.title());
            pageResponseDto.setUrl(link);
            pageResponseDto.setHostUrl(constructUrl(getHostUrl(link)));

            constructWebPageInternalLinks(link, elements, pageResponseDto);
            return pageResponseDto;
        } catch (Exception exception) {
            throw new LogicalException(FAILED_TO_CRAWEL_THIS_WEBSITE);
        }

    }

    public static String constructUrl(String nextUrl) {
        if (nextUrl.endsWith("#")) {
            nextUrl = nextUrl.substring(0, nextUrl.length() - 1);
        }
        if (nextUrl.endsWith("/")) {
            nextUrl = nextUrl.substring(0, nextUrl.length() - 1);
        }
        return nextUrl.replace("www.", "");
    }

    private static void constructWebPageInternalLinks(String link, Elements elements, PageResponseDto pageResponseDto) {
        HashSet<String> links = new HashSet<>();
        links.add(constructUrl(link));
        for (Element element : elements) {
            String nextUrl = constructUrl(element.absUrl("href"));
            if (CrawelingUtils.isValidInternalLink(nextUrl, pageResponseDto.getHostUrl())
                    && !links.contains(nextUrl)) {
                pageResponseDto.getLinks().add(nextUrl);
                links.add(nextUrl);
            }
        }
    }
}
