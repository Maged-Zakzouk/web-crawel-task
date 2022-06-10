package com.boost.webcrawel;

import io.restassured.RestAssured;
import org.junit.ClassRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = WebCrawelApplication.class)
@Sql(scripts = {"/sql/Cleanup.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Testcontainers
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @ClassRule
    public static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"))
            .withEmbeddedZookeeper();

    @BeforeAll
    public static void setUp() {
        RestAssured.port = 8088;
        RestAssured.baseURI = "http://localhost/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    }

    @DynamicPropertySource
    static void dynamicPropertySource(DynamicPropertyRegistry dynamicPropertyRegistry) {

        MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.26")
                .withDatabaseName("boost-crawel")
                .withUsername("root")
                .withPassword("root")
                .withEnv("MYSQL_ROOT_HOST", "%");

        dynamicPropertyRegistry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", mysqlContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", mysqlContainer::getPassword);
        dynamicPropertyRegistry.add("spring.datasource.driver-class", () -> "com.mysql.cj.jdbc.Driver");
        dynamicPropertyRegistry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQL8Dialect");

        mysqlContainer.start();
        kafka.start();

        dynamicPropertyRegistry.add("web.craweler.kafka.bootstrapAddress", kafka::getBootstrapServers);
        dynamicPropertyRegistry.add("web.craweler.kafka.topicName", () -> "craweler-topic");
        dynamicPropertyRegistry.add("web.craweler.kafka.consumer.groupId", () -> "craweler-group");

        System.out.println("Containers should be started.");
    }

}
