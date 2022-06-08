package com.boost.webcrawel.controller;

import com.boost.webcrawel.BaseIntegrationTest;
import com.boost.webcrawel.core.crawel.dto.response.WebsiteResponseDto;
import com.boost.webcrawel.core.crawel.entity.Status;
import com.boost.webcrawel.core.crawel.entity.WebPage;
import com.boost.webcrawel.core.crawel.entity.Website;
import com.boost.webcrawel.core.crawel.repo.WebPageRepo;
import com.boost.webcrawel.core.crawel.repo.WebsiteRepo;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static com.boost.webcrawel.core.commons.exception.ServerError.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

// @formatter:off
public class GetWebsiteCraweledPagesTests extends BaseIntegrationTest {

    @Autowired
    WebsiteRepo websiteRepo;

    @Autowired
    WebPageRepo webPageRepo;

    @Test
    public void getWebsiteCraweledPages_WhenURLIsInvalid_ThrowsInvalidURLException(){
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .get("/v1/web-crawel?url=https://www.monzo")
        .then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", CoreMatchers.equalTo(INVALID_URL.getMessage()));
    }

    @Test
    public void getWebsiteCraweledPages_WhenURLIsInvalid2_ThrowsInvalidURLException(){
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .get("/v1/web-crawel?url=www.monzo.com")
        .then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", CoreMatchers.equalTo(INVALID_URL.getMessage()));
    }

    @Test
    public void getWebsiteCraweledPages_WhenURLIsValidButUnExisted_ThrowsUnExistedWebsiteException(){
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .get("/v1/web-crawel?url=https://www.monzo.com")
        .then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", CoreMatchers.equalTo(UNREGISTERED_URL.getMessage()));
    }

    @Test
    public void getWebsiteCraweledPages_WhenURLIsValidButStillPending_ThrowsWebsiteUnderCrawelingException(){
        Website website = new Website();
        website.setUrl("https://www.monzo.com");
        websiteRepo.save(website);

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .get("/v1/web-crawel?url=" + website.getUrl())
        .then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", CoreMatchers.equalTo(WEBSITE_UNDER_CRAWLING.getMessage()));
    }

    @Test
    public void getWebsiteCraweledPages_WhenURLIsValidButWithInvalidPageAttribute_ThrowsInvalidPageException(){
        Website website = new Website();
        website.setUrl("https://www.monzo.com");
        website.setStatus(Status.ACTIVE);
        websiteRepo.save(website);

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .get("/v1/web-crawel?url=https://www.monzo.com&page=-1&size=10")
        .then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", CoreMatchers.equalTo(INVALID_PAGE.getMessage()));
    }

    @Test
    public void getWebsiteCraweledPages_WhenURLIsValidAndExisted_ReturnWebsiteWithItsLinks(){

        Website website = new Website();
        website.setUrl("https://www.monzo.com");
        website.setStatus(Status.ACTIVE);
        website = websiteRepo.save(website);

        List<WebPage> webPages = new ArrayList<>();
        webPages.add(new WebPage("test1", website));
        webPages.add(new WebPage("test2", website));
        webPages.add(new WebPage("test3", website));
        webPages.add(new WebPage("test4", website));
        webPages.add(new WebPage("test5", website));
        webPageRepo.saveAll(webPages);

        WebsiteResponseDto websiteResponseDto =
            given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
            .when()
                .get("/v1/web-crawel?url=https://www.monzo.com&page=1&size=10")
            .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(WebsiteResponseDto.class);

        assertEquals(1, websiteResponseDto.getPages().getTotalPages());
        assertEquals(5, websiteResponseDto.getPages().getTotalItems());
    }


}
