package br.com.erudio.integrationtests.testcontainers;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

/**
 * The `@ContextConfiguration` annotation is used in Spring integration tests to load
 * the application context with specific configurations. It allows specifying configuration
 * classes or locations of configuration files that should be used to initialize the Spring
 * context during tests.
 * <p>
 * In this case, the annotation is used to initialize the context with the `Initializer` class,
 * which configures and starts a MySQL container using Testcontainers. This enables the integration
 * tests to run in a controlled and consistent environment, utilizing a MySQL database in a container.
 */
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {
  public class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.29")
        .withDatabaseName("integration-tests-db")
        .withUsername("sa")
        .withPassword("sa");

    private static void startContainers() {
      Startables.deepStart(Stream.of(mysql)).join();
    }

    private static Map<String, String> createConnectionConfiguration() {
      return Map.of(
          "spring.datasource.url", mysql.getJdbcUrl(),
          "spring.datasource.username", mysql.getUsername(),
          "spring.datasource.password", mysql.getPassword()
      );
    }

    /**
     * The SuppressWarnings annotation tells the compiler to suppress specific warnings that it would
     * otherwise generate. In this case, it is used to suppress unchecked warnings for the cast to Map.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      startContainers();
      ConfigurableEnvironment enviroment = applicationContext.getEnvironment();

      MapPropertySource testcontainers =
          new MapPropertySource("testcontainers", (Map) createConnectionConfiguration());

      enviroment.getPropertySources().addFirst(testcontainers);
    }

  }
}
