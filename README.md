# Web Crawel Task

Scope of this Project:
-------------------------
This is a sample project for crawelling web site and get the site map of this site, we have defined crawelling-depth we
can change from apllication.properties to determine number of levels in crawelling.

The project has two APIs:
------------------------------------------

    * POST for crawel new website

This API for crawelling new website,

Path: v1/web-crawel

Request body:

{
"url": ""
}

    * GET for getting crawelled website

This API for getting crawelled website,

Path: v1/web-crawel?url=https://www.xyz.com&page=1&size=10

will get pages paginated since there are websites with large size of pages

Buit by the following technologies:
------------------------------------------

    * Java 11
    * Spring boot
    * Maven
    * Spring Data JPA
    * Kafka
    * Test Containers for integration test

Command-line Instructions and Required Technologies
-------------------------
First methodology to run the application is to have the following:

    * Java 11
    * Spring boot
    * Maven
    * Spring Data JPA
    * Kafka
    * Test Containers for integration test

command lines to run:

```bash
cd 'file location'
mvn clean install
mvn mvn spring-boot:run
```

First methodology to run the application is to have the following:

    * Docker (check solve-docker-compose-kafka-issue to run it by docker)

command lines to run:

```bash
cd 'file location'
docker-compose up
```
