package br.com.erudio.unittests.mockito.services;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    List<Person> list = input.mockEntityList();

    when(repository.findAll()).thenReturn(list);
    var people = services.findAll();

    assertNotNull(people);
    assertEquals(14, people.size());

    var personOne = people.get(1);

    assertNotNull(personOne);
    assertNotNull(personOne.getKey());
    assertNotNull(personOne.getLinks());

    assertTrue(personOne.toString().contains("links: [</api/person/v1/1>;rel=\"self\"]"));

    assertEquals("Addres Test1", personOne.getAddress());
    assertEquals("First Name Test1", personOne.getFirstName());
    assertEquals("Last Name Test1", personOne.getLastName());
    assertEquals("Female", personOne.getGender());

    var personFour = people.get(4);

    assertNotNull(personFour);
    assertNotNull(personFour.getKey());
    assertNotNull(personFour.getLinks());

    assertTrue(personFour.toString().contains("links: [</api/person/v1/4>;rel=\"self\"]"));

    assertEquals("Addres Test4", personFour.getAddress());
    assertEquals("First Name Test4", personFour.getFirstName());
    assertEquals("Last Name Test4", personFour.getLastName());
    assertEquals("Male", personFour.getGender());

    var personSeven = people.get(7);

    assertNotNull(personSeven);
    assertNotNull(personSeven.getKey());
    assertNotNull(personSeven.getLinks());

    assertTrue(personSeven.toString().contains("links: [</api/person/v1/7>;rel=\"self\"]"));

    assertEquals("Addres Test7", personSeven.getAddress());
    assertEquals("First Name Test7", personSeven.getFirstName());
    assertEquals("Last Name Test7", personSeven.getLastName());
    assertEquals("Female", personSeven.getGender());
  }

  @Test
  void testFindById() {
    Person entity = input.mockEntity(1);
    /*
      Id needs to be set manually because the mockEntity method
      does not set the id field.
     */
    entity.setId(1L);

    /*
      The when() method is used to configure the behavior of the mock object.
      In this case, we are configuring the behavior of the repository from
      PersonService, making it being intercepted by mockito to return a mocked
      object when the findById method is called, instead of actually accessing
      the database.
     */
    when(repository.findById(1L)).thenReturn(Optional.of(entity));

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
    /*
      Here, we're gonna need an extra Person object (which is the persisted object).

      The entity object is the object that we are going to pass to the repository
      to simulate the persistence of a new object in the database.

      The persisted object is the object that it's going to be returned when the
      repository.save() method is called, simulating the object that was persisted
      in the database, and that was returned by repository.save().
     */
    Person entity = input.mockEntity(1);
    Person persisted = entity;

    /*
      Id needs to be set manually because the mockEntity method
      does not set the id field. And, since we want to simulate
      an persisted object, we need to set its id field.
     */
    persisted.setId(1L);

    PersonVO vo = input.mockVO(1);
    vo.setKey(1L);

    when(repository.save(entity)).thenReturn(persisted);

    var result = services.create(vo);

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
  void testCreateWithNullPerson() {
    Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
      services.create(null);
    });

    String expectedMessage = "It is not allowed to persist a null object!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void testUpdate() {
    Person entity = input.mockEntity(1);
    entity.setId(1L);

    Person persisted = entity;
    persisted.setId(1L);

    PersonVO vo = input.mockVO(1);
    vo.setKey(1L);

    /*
      Here, it's necessary to mock both findById and save methods from
      the repository, because the PersonServices.update() function uses
      these two methods to first search for the existing object, and the
      for saving the updated object.
     */
    when(repository.findById(1L)).thenReturn(Optional.of(entity));
    when(repository.save(entity)).thenReturn(persisted);

    var result = services.update(vo);

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
  void testUpdateWithNullPerson() {
    Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
      services.update(null);
    });

    String expectedMessage = "It is not allowed to persist a null object!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void testDelete() {
    Person entity = input.mockEntity(1);
    entity.setId(1L);

    when(repository.findById(1L)).thenReturn(Optional.of(entity));

    services.delete(1L);
  }
}