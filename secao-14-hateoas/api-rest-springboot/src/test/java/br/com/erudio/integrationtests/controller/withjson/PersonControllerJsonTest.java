package br.com.erudio.integrationtests.controller.withjson;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.PersonVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The `@SpringBootTest` annotation is used to load the complete application context for integration tests.
 * This allows the tests to run in an environment that closely simulates the real application.
 * <p>
 * The `webEnvironment` parameter defines the web environment to be used during the tests.
 * It can take different values from the `SpringBootTest.WebEnvironment` enumeration:
 * <p>
 * - `MOCK`: Uses a mock web environment. No real server is started.
 * - `RANDOM_PORT`: Starts a web server on a random port.
 * - `DEFINED_PORT`: Starts a web server on the port defined in the application configuration.
 * - `NONE`: No web environment is started.
 * <p>
 * In this test class, the value `DEFINED_PORT` is used, meaning the web server will start on the
 * port defined in the application configuration.
 * This is useful for integration tests that need a real web server to verify HTTP endpoint interactions.
 * <p>
 * The `@TestMethodOrder` annotation is used to configure the order in which test methods are executed.
 * The `MethodOrderer.OrderAnnotation.class` parameter specifies that the order is determined by the
 * `@Order` annotation on each test method. This allows for explicit control over the sequence of test execution.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonControllerJsonTest extends AbstractIntegrationTest {

  /**
   * The `specification` field is a static instance of `RequestSpecification` from RestAssured.
   * It is used to define the common settings for HTTP requests, such as headers, base path, port,
   * and logging filters. This allows for consistent and reusable configurations across multiple tests.
   */
  private static RequestSpecification specification;

  /**
   * The `objectMapper` field is a static instance of `ObjectMapper` from the Jackson library.
   * It is used to serialize and deserialize Java objects to and from JSON. This is essential for
   * handling JSON payloads in the integration tests, ensuring that the data can be correctly
   * converted between Java objects and JSON format.
   */
  private static ObjectMapper objectMapper;

  private static PersonVO person;

  @BeforeAll
  public static void setUp() {
    objectMapper = new ObjectMapper();
    /**
     * The `disable` method on the `objectMapper` instance is used to configure the deserialization
     * behavior. By calling `disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)`, the
     * `objectMapper` is instructed to ignore any unknown properties in the JSON payloads during
     * deserialization. This prevents errors when the JSON contains fields that are not present
     * in the target Java object, making the deserialization process more robust and flexible.
     */
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    person = new PersonVO();
  }

  @Test
  /**
   * The `@Order` annotation is used to specify the order in which test methods are executed.
   * In this case, `@Order(1)` indicates that the `testCreate` method should be executed first.
   * This is useful for controlling the sequence of test execution, ensuring that certain tests
   * run before others, which can be important for setting up the necessary state or dependencies
   * for subsequent tests.
   */
  @Order(1)
  public void testCreate() throws IOException {
    mockPerson();

    /**
     * The code block below creates a `RequestSpecification` instance using the `RequestSpecBuilder`.
     * It sets up the common configuration for HTTP requests, including adding a header, setting the
     * base path and port, and adding logging filters for request and response details. This
     * configuration is then built and assigned to the `specification` field, which will be used
     * in the subsequent HTTP requests to ensure consistent settings.
     */
    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
        .setBasePath("/api/person/v1")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    /**
     * The code block below sends an HTTP POST request to create a new person using the `specification`
     * for configuration. It sets the content type to JSON and includes the `person` object in the
     * request body. The response is then validated to ensure a status code of 200 (OK) and the
     * response body is extracted as a string. This string represents the JSON response from the
     * server, which contains the details of the created person.
     */
    var content = given().spec(specification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .body(person)
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    /**
     * The `objectMapper` is used here to deserialize the JSON response content into a `PersonVO`
     * object. The `readValue` method converts the JSON string into an instance of `PersonVO`,
     * allowing the test to work with a strongly-typed Java object. This deserialized object is
     * then assigned to the `person` field and various assertions are made to verify that the
     * created person has the expected properties and values.
     */
    PersonVO createdPerson = objectMapper.readValue(content, PersonVO.class);
    person = createdPerson;

    assertNotNull(createdPerson);

    assertNotNull(createdPerson.getId());
    assertNotNull(createdPerson.getFirstName());
    assertNotNull(createdPerson.getLastName());
    assertNotNull(createdPerson.getAddress());
    assertNotNull(createdPerson.getGender());

    assertTrue(createdPerson.getId() > 0);

    assertEquals("Richard", createdPerson.getFirstName());
    assertEquals("Stallman", createdPerson.getLastName());
    assertEquals("New York City, New York, US", createdPerson.getAddress());
    assertEquals("Male", createdPerson.getGender());
  }

  @Test
  @Order(2)
  public void testCreateWithWrongOrigin() throws IOException {
    mockPerson();

    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
        .setBasePath("/api/person/v1")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    var content = given().spec(specification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .body(person)
        .when()
        .post()
        .then()
        .statusCode(403)
        .extract()
        .body()
        .asString();

    assertNotNull(content);
    assertEquals("Invalid CORS request", content);
  }

  @Test
  @Order(3)
  public void testFindById() throws IOException {
    mockPerson();

    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
        .setBasePath("/api/person/v1")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    var content = given().spec(specification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .pathParam("id", person.getId())
        .when()
        .get("{id}")
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
    person = persistedPerson;

    assertNotNull(persistedPerson);

    assertNotNull(persistedPerson.getId());
    assertNotNull(persistedPerson.getFirstName());
    assertNotNull(persistedPerson.getLastName());
    assertNotNull(persistedPerson.getAddress());
    assertNotNull(persistedPerson.getGender());

    assertTrue(persistedPerson.getId() > 0);

    assertEquals("Richard", persistedPerson.getFirstName());
    assertEquals("Stallman", persistedPerson.getLastName());
    assertEquals("New York City, New York, US", persistedPerson.getAddress());
    assertEquals("Male", persistedPerson.getGender());
  }

  @Test
  @Order(4)
  public void testFindByIdWithWrongOrigin() throws IOException {
    mockPerson();

    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_SEMERU)
        .setBasePath("/api/person/v1")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    var content = given().spec(specification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .pathParam("id", person.getId())
        .when()
        .get("{id}")
        .then()
        .statusCode(403)
        .extract()
        .body()
        .asString();

    assertNotNull(content);
    assertEquals("Invalid CORS request", content);
  }

  private void mockPerson() {
    person.setFirstName("Richard");
    person.setLastName("Stallman");
    person.setAddress("New York City, New York, US");
    person.setGender("Male");
  }
}
