package com.boost.webcrawel.controller;

import com.boost.webcrawel.BaseIntegrationTest;
import com.boost.webcrawel.core.crawel.dto.request.WebCrawelRequest;
import com.boost.webcrawel.core.crawel.entity.Website;
import com.boost.webcrawel.core.crawel.repo.WebsiteRepo;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static com.boost.webcrawel.core.commons.exception.ServerError.INVALID_URL;
import static com.boost.webcrawel.core.commons.exception.ServerError.REGISTERED_BEFORE_URL;
import static io.restassured.RestAssured.given;

// @formatter:off
public class CrawelWebsiteAPITests extends BaseIntegrationTest{

    @Autowired
    private WebsiteRepo websiteRepo;

    @Test
    public void crawelNewWebsite_WhenURLIsNull_ReturnBadRequest(){

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(new WebCrawelRequest())
        .when()
            .post("/v1/web-crawel")
        .then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void crawelNewWebsite_WhenURLIsInvalid_ThrowsInvalidURLException(){

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(new WebCrawelRequest("https://www.monzo"))
        .when()
            .post("/v1/web-crawel")
        .then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", CoreMatchers.equalTo(INVALID_URL.getMessage()));
    }

    @Test
    public void crawelNewWebsite_WhenURLIsInvalid2_ThrowsInvalidURLException(){
        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(new WebCrawelRequest("www.monzo.com"))
        .when()
            .post("/v1/web-crawel")
        .then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", CoreMatchers.equalTo(INVALID_URL.getMessage()));
    }

    @Test
    public void crawelNewWebsite_WhenURLIsValidButExistedBefore_ThrowsRegisteredBeforeURLException(){
        Website website = new Website();
        website.setUrl("https://www.monzo.com");
        websiteRepo.save(website);

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(new WebCrawelRequest(website.getUrl()))
        .when()
            .post("/v1/web-crawel")
        .then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", CoreMatchers.equalTo(REGISTERED_BEFORE_URL.getMessage()));
    }

    @Test
    public void crawelNewWebsite_WhenURLIsValidButBaseURLExistedBefore_ThrowsRegisteredBeforeURLException(){
        Website website = new Website();
        website.setUrl("https://www.monzo.com");
        websiteRepo.save(website);

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(new WebCrawelRequest(website.getUrl() + "/info"))
        .when()
            .post("/v1/web-crawel")
        .then()
            .assertThat()
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .body("message", CoreMatchers.equalTo(REGISTERED_BEFORE_URL.getMessage()));
    }

    @Test
    public void crawelNewWebsite_WhenURLIsValidAndUnExistedBefore_ReturnSuccessedResponse(){

        given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(new WebCrawelRequest("https://www.monzo.com"))
        .when()
            .post("/v1/web-crawel")
        .then()
            .assertThat()
            .statusCode(HttpStatus.OK.value())
            .body("message", CoreMatchers.equalTo("Your link is being craweling now"));
    }

}
