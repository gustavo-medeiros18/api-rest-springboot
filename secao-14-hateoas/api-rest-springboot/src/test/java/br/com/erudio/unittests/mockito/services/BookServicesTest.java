package br.com.erudio.unittests.mockito.services;

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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
  void testDelete() {
    Book entity = input.mockEntity(1);
    entity.setId(1L);

    when(repository.findById(1L)).thenReturn(Optional.of(entity));
    services.delete(1L);
  }
}
