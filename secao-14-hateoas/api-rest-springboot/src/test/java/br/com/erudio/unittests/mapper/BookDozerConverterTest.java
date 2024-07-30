package br.com.erudio.unittests.mapper;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookDozerConverterTest {
  MockBook inputObject;

  @BeforeEach
  public void setUp() {
    inputObject = new MockBook();
  }

  @Test
  public void parseEntityToVOTest() {
    BookVO output = DozerMapper.parseObject(inputObject.mockEntity(), BookVO.class);
    assertEquals(Long.valueOf(0L), output.getKey());
    assertEquals("Author Test0", output.getAuthor());
    assertEquals("2020-01-01", output.getLaunchDate());
    assertEquals(Double.valueOf(10), output.getPrice());
    assertEquals("Title Test0", output.getTitle());
  }

  @Test
  public void parseEntityListToVOListTest() {
    List<BookVO> outputList = DozerMapper.parseListObjects(inputObject.mockEntityList(), BookVO.class);
    BookVO outputZero = outputList.get(0);

    assertEquals(Long.valueOf(0L), outputZero.getKey());
    assertEquals("Author Test0", outputZero.getAuthor());
    assertEquals("2020-01-01", outputZero.getLaunchDate());
    assertEquals(Double.valueOf(10), outputZero.getPrice());
    assertEquals("Title Test0", outputZero.getTitle());

    BookVO outputSeven = outputList.get(7);

    assertEquals(Long.valueOf(7L), outputSeven.getKey());
    assertEquals("Author Test7", outputSeven.getAuthor());
    assertEquals("2020-01-01", outputSeven.getLaunchDate());
    assertEquals((10.0 + 7), outputSeven.getPrice());
    assertEquals("Title Test7", outputSeven.getTitle());

    BookVO outputTwelve = outputList.get(12);

    assertEquals(Long.valueOf(12L), outputTwelve.getKey());
    assertEquals("Author Test12", outputTwelve.getAuthor());
    assertEquals("2020-01-01", outputTwelve.getLaunchDate());
    assertEquals((10.0 + 12), outputTwelve.getPrice());
    assertEquals("Title Test12", outputTwelve.getTitle());
  }

  @Test
  public void parseVOToEntityTest() {
    Book output = DozerMapper.parseObject(inputObject.mockVO(), Book.class);

    assertEquals(Long.valueOf(0L), output.getId());
    assertEquals("Author Test0", output.getAuthor());
    assertEquals("2020-01-01", output.getLaunchDate());
    assertEquals(Double.valueOf(10), output.getPrice());
    assertEquals("Title Test0", output.getTitle());
  }

  @Test
  public void parserVOListToEntityListTest() {
    List<Book> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Book.class);
    Book outputZero = outputList.get(0);

    assertEquals(Long.valueOf(0L), outputZero.getId());
    assertEquals("Author Test0", outputZero.getAuthor());
    assertEquals("2020-01-01", outputZero.getLaunchDate());
    assertEquals(Double.valueOf(10), outputZero.getPrice());
    assertEquals("Title Test0", outputZero.getTitle());

    Book outputSeven = outputList.get(7);

    assertEquals(Long.valueOf(7L), outputSeven.getId());
    assertEquals("Author Test7", outputSeven.getAuthor());
    assertEquals("2020-01-01", outputSeven.getLaunchDate());
    assertEquals((10.0 + 7), outputSeven.getPrice());
    assertEquals("Title Test7", outputSeven.getTitle());

    Book outputTwelve = outputList.get(12);

    assertEquals(Long.valueOf(12L), outputTwelve.getId());
    assertEquals("Author Test12", outputTwelve.getAuthor());
    assertEquals("2020-01-01", outputTwelve.getLaunchDate());
    assertEquals(10.0 + 12, outputTwelve.getPrice());
    assertEquals("Title Test12", outputTwelve.getTitle());
  }
}
