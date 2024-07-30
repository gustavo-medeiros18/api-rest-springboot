package br.com.erudio.unittests.mapper;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.unittests.mapper.mocks.MockBook;
import br.com.erudio.unittests.mapper.mocks.MockPerson;
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
    assertEquals(Double.valueOf(10), outputSeven.getPrice());
    assertEquals("Title Test7", outputSeven.getTitle());

    BookVO outputTwelve = outputList.get(12);

    assertEquals(Long.valueOf(12L), outputTwelve.getKey());
    assertEquals("Author Test12", outputSeven.getAuthor());
    assertEquals("2020-01-01", outputSeven.getLaunchDate());
    assertEquals(Double.valueOf(10), outputSeven.getPrice());
    assertEquals("Title Test12", outputSeven.getTitle());
  }

//  @Test
//  public void parseVOToEntityTest() {
//    Person output = DozerMapper.parseObject(inputObject.mockVO(), Person.class);
//    assertEquals(Long.valueOf(0L), output.getId());
//    assertEquals("First Name Test0", output.getFirstName());
//    assertEquals("Last Name Test0", output.getLastName());
//    assertEquals("Addres Test0", output.getAddress());
//    assertEquals("Male", output.getGender());
//  }
//
//  @Test
//  public void parserVOListToEntityListTest() {
//    List<Person> outputList = DozerMapper.parseListObjects(inputObject.mockVOList(), Person.class);
//    Person outputZero = outputList.get(0);
//
//    assertEquals(Long.valueOf(0L), outputZero.getId());
//    assertEquals("First Name Test0", outputZero.getFirstName());
//    assertEquals("Last Name Test0", outputZero.getLastName());
//    assertEquals("Addres Test0", outputZero.getAddress());
//    assertEquals("Male", outputZero.getGender());
//
//    Person outputSeven = outputList.get(7);
//
//    assertEquals(Long.valueOf(7L), outputSeven.getId());
//    assertEquals("First Name Test7", outputSeven.getFirstName());
//    assertEquals("Last Name Test7", outputSeven.getLastName());
//    assertEquals("Addres Test7", outputSeven.getAddress());
//    assertEquals("Female", outputSeven.getGender());
//
//    Person outputTwelve = outputList.get(12);
//
//    assertEquals(Long.valueOf(12L), outputTwelve.getId());
//    assertEquals("First Name Test12", outputTwelve.getFirstName());
//    assertEquals("Last Name Test12", outputTwelve.getLastName());
//    assertEquals("Addres Test12", outputTwelve.getAddress());
//    assertEquals("Male", outputTwelve.getGender());
//  }
}
