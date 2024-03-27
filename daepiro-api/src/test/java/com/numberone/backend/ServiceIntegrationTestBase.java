package com.numberone.backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;


@Slf4j
@SpringBootTest(classes = ServiceIntegrationTestConfiguration.class)
@Import({CoreTestConfiguration.class})
public abstract class ServiceIntegrationTestBase {

    private static final String REDIS_DOCKER_IMAGE = "redis:5.0.3-alpine";
    private static final String MYSQL_DOCKER_IMAGE = "mysql:8.0";
    private static final String MYSQL_ROOT = "root";
    private static final String MYSQL_PASSWORD = "1234";

    private static final int REDIS_PORT = 6379;

    @Container
    protected static MySQLContainer mySQLContainer;

    @Container
    protected static GenericContainer redisContainer;

    @DynamicPropertySource
    static void configureProperties(final DynamicPropertyRegistry registry) {
        // mysql env init
        log.info("""
                    \n
                    ✅ mysql property 를 주입합니다.
                """);
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", () -> MYSQL_ROOT);
        registry.add("spring.datasource.password", () -> MYSQL_PASSWORD);

        // redis env init
        log.info("""
                    \n
                    ✅ redis property 를 주입합니다.
                """);
        registry.add("spring.redis.host", redisContainer::getHost);
        registry.add("spring.redis.port", () -> "" + redisContainer.getMappedPort(REDIS_PORT));
    }

    static {
        // mysql test container init
        mySQLContainer = (MySQLContainer) new MySQLContainer(MYSQL_DOCKER_IMAGE)
                .withUsername(MYSQL_ROOT)
                .withPassword(MYSQL_PASSWORD)
                .withDatabaseName("test")
                .withEnv("MYSQL_ROOT_PASSWORD", MYSQL_PASSWORD);

        log.info("""
                \n
                🐳 MYSQL 테스트 컨테이너를 시작합니다.
                - base image: {}
                """, MYSQL_DOCKER_IMAGE);
        mySQLContainer.start();

        // redis test container init
        redisContainer = new GenericContainer<>(REDIS_DOCKER_IMAGE)
                .withExposedPorts(REDIS_PORT)
                .withReuse(true);
        log.info("""
                \n
                🐳 Redis 테스트 컨테이너를 시작합니다.
                - base image: {}
                """, REDIS_DOCKER_IMAGE);
        redisContainer.start();
    }

}
