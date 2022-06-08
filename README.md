# Web Crawel Task


Scope of this Project:
-------------------------
  This is a sample project for crawelling web site and get the site map of this site, we have defined crawelling-depth we can change from apllication.properties to determine number of levels in crawelling.
  
The project has two APIs:
    * POST for crawel new website
    * GET for getting crawelled website


* **Buit by the following technologies:**
------------------------------------------
    * Java 11
    * Spring boot
    * Maven
    * Spring Data JPA
    * Kafka
    * Test Containers for integration test


Command-line Instructions
-------------------------

```bash
cd 'file location'
# Compile and run
mvn clean install
mvn exec:java -Dexec.mainClass=com.boost.webcrawel.WebCrawelApplication
```

