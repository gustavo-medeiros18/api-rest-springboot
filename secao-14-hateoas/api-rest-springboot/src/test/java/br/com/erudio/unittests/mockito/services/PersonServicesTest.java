package br.com.erudio.unittests.mockito.services;

import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import br.com.erudio.services.PersonServices;
import br.com.erudio.unittests.mapper.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * The TestInstance annotation is used to configure the test lifecycle.
 * By default, JUnit creates a new instance of the test class for each test method.
 * <p>
 * If you annotate your test class with @TestInstance(Lifecycle.PER_CLASS),
 * JUnit will create only one instance of the test class and reuse it for all
 * test methods of that test class.
 * <p>
 * The ExtendWith annotation is used to register extensions for the annotated test class
 * or test method. Here, we are using the MockitoExtension.class extension to enable
 * Mockito annotations.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {
  /**
   * This input here is a mock object that we will use to simulate the
   * input data for the tests. We are using the MockPerson class to create
   * a mock object with some predefined values.
   */
  MockPerson input;

  /**
   * The @InjectMocks annotation is used to inject mock fields into
   * the tested object automatically. It is used to inject mock or
   * spy fields into tested objects automatically.
   */
  @InjectMocks
  private PersonServices services;

  /**
   * The @Mock annotation is used to create and inject mocked instances.
   */
  @Mock
  PersonRepository repository;

  @BeforeEach
  void setUpMocks() throws Exception {
    input = new MockPerson();

    /*
      This method initializes objects annotated with Mockito annotations,
      such as @Mock, @Spy, @Captor, and @InjectMocks.
     */
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() {
  }

  @Test
  void testFindById() {
    Person person = input.mockEntity(1);
    /*
      Id needs to be set manually because the mockEntity method
      does not set the id field.
     */
    person.setId(1L);

    /*
      The when() method is used to configure the behavior of the mock object.
      In this case, we are configuring the behavior of the repository from
      PersonService, making it being intercepted by mockito to return a mocked
      object when the findById method is called, instead of actually accessing
      the database.
     */
    when(repository.findById(1L)).thenReturn(Optional.of(person));

    var result = services.findById(1L);

    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());

    assertTrue(result.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

    assertEquals("Addres Test1", result.getAddress());
    assertEquals("First Name Test1", result.getFirstName());
    assertEquals("Last Name Test1", result.getLastName());
    assertEquals("Female", result.getGender());
  }

  @Test
  void testCreate() {
  }

  @Test
  void testUpdate() {
  }

  @Test
  void testDelete() {
  }
}