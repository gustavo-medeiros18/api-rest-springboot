package br.com.erudio.unittests.mockito.services;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;
import br.com.erudio.services.BookServices;
import br.com.erudio.unittests.mapper.mocks.MockBook;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BookServicesTest {
  MockBook input;

  @InjectMocks
  private BookServices services;

  @Mock
  BookRepository repository;

  @BeforeEach
  void setUpMocks() throws Exception {
    input = new MockBook();

    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() {
    List<Book> list = input.mockEntityList();

    when(repository.findAll()).thenReturn(list);
    var books = services.findAll();

    assertNotNull(books);
    assertEquals(14, books.size());

    var bookOne = books.get(1);

    assertNotNull(bookOne);
    assertNotNull(bookOne.getKey());
    assertNotNull(bookOne.getLinks());

    assertTrue(bookOne.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));

    assertEquals("Author Test1", bookOne.getAuthor());
    assertEquals("2020-01-01", bookOne.getLaunchDate());
    assertEquals(Double.valueOf(11), bookOne.getPrice());
    assertEquals("Title Test1", bookOne.getTitle());

    var bookFour = books.get(4);

    assertNotNull(bookFour);
    assertNotNull(bookFour.getKey());
    assertNotNull(bookFour.getLinks());

    assertTrue(bookFour.toString().contains("links: [</api/book/v1/4>;rel=\"self\"]"));

    assertEquals("Author Test4", bookFour.getAuthor());
    assertEquals("2020-01-01", bookFour.getLaunchDate());
    assertEquals(Double.valueOf(14), bookFour.getPrice());
    assertEquals("Title Test4", bookFour.getTitle());

    var bookSeven = books.get(7);

    assertNotNull(bookSeven);
    assertNotNull(bookSeven.getKey());
    assertNotNull(bookSeven.getLinks());

    assertTrue(bookSeven.toString().contains("links: [</api/book/v1/7>;rel=\"self\"]"));

    assertEquals("Author Test7", bookSeven.getAuthor());
    assertEquals("2020-01-01", bookSeven.getLaunchDate());
    assertEquals(Double.valueOf(17), bookSeven.getPrice());
    assertEquals("Title Test7", bookSeven.getTitle());
  }

  @Test
  void testFindById() {
    Book entity = input.mockEntity(1);
    entity.setId(1L);

    when(repository.findById(1L)).thenReturn(Optional.of(entity));
    var result = services.findById(1L);

    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());

    assertEquals(Long.valueOf(1L), result.getKey());
    assertEquals("Author Test1", result.getAuthor());
    assertEquals("2020-01-01", result.getLaunchDate());
    assertEquals(Double.valueOf(11), result.getPrice());
    assertEquals("Title Test1", result.getTitle());
  }

  @Test
  void testCreate() {
    Book entity = input.mockEntity(1);
    Book persisted = entity;

    persisted.setId(1L);

    BookVO vo = input.mockVO(1);
    vo.setKey(1L);

    when(repository.save(entity)).thenReturn(persisted);
    var result = services.create(vo);

    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());

    assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));

    assertEquals("Author Test1", result.getAuthor());
    assertEquals("2020-01-01", result.getLaunchDate());
    assertEquals(Double.valueOf(11), result.getPrice());
    assertEquals("Title Test1", result.getTitle());
  }

  @Test
  void testCreateWithNullBook() {
    Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
      services.create(null);
    });

    String expectedMessage = "It is not allowed to persist a null object!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void testUpdate() {
    Book entity = input.mockEntity(1);
    entity.setId(1L);

    Book persisted = entity;
    persisted.setId(1L);

    BookVO vo = input.mockVO(1);
    vo.setKey(1L);

    when(repository.findById(1L)).thenReturn(Optional.of(entity));
    when(repository.save(entity)).thenReturn(persisted);

    var result = services.update(vo);

    assertNotNull(result);
    assertNotNull(result.getKey());
    assertNotNull(result.getLinks());

    assertTrue(result.toString().contains("links: [</api/book/v1/1>;rel=\"self\"]"));

    assertEquals("Author Test1", result.getAuthor());
    assertEquals("2020-01-01", result.getLaunchDate());
    assertEquals(Double.valueOf(11), result.getPrice());
    assertEquals("Title Test1", result.getTitle());
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
  void testUpdateWithUnknownId() {
    BookVO vo = input.mockVO(1);

    when(repository.findById(1L)).thenReturn(Optional.empty());
    Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
      services.update(vo);
    });

    String expectedMessage = "No records found for this ID!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  @Test
  void testDelete() {
    Book entity = input.mockEntity(1);
    entity.setId(1L);

    when(repository.findById(1L)).thenReturn(Optional.of(entity));
    services.delete(1L);
  }

  @Test
  void testDeleteWithUnknownId() {
    when(repository.findById(1L)).thenReturn(Optional.empty());

    Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
      services.delete(1L);
    });

    String expectedMessage = "No records found for this ID!";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
}
