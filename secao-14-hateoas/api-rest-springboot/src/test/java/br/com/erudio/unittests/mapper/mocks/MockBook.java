package br.com.erudio.unittests.mapper.mocks;

import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.model.Book;

import java.util.ArrayList;
import java.util.List;

public class MockBook {
  public Book mockEntity() {
    return mockEntity(0);
  }

  public BookVO mockVO() {
    return mockVO(0);
  }

  public List<Book> mockEntityList() {
    List<Book> books = new ArrayList<Book>();

    for (int i = 0; i < 14; i++)
      books.add(mockEntity(i));

    return books;
  }

  public List<BookVO> mockVOList() {
    List<BookVO> books = new ArrayList<BookVO>();

    for (int i = 0; i < 14; i++)
      books.add(mockVO(i));

    return books;
  }

  public Book mockEntity(Integer number) {
    Book book = new Book();

    book.setAuthor("Author Test" + number);
    book.setLaunchDate("2020-01-01");
    book.setPrice(Double.valueOf(10 + number));
    book.setTitle("Title Test" + number);
    book.setId(number.longValue());

    return book;
  }

  public BookVO mockVO(Integer number) {
    BookVO book = new BookVO();

    book.setAuthor("Author Test" + number);
    book.setLaunchDate("2020-01-01");
    book.setPrice(Double.valueOf(10 + number));
    book.setTitle("Title Test" + number);
    book.setKey(number.longValue());

    return book;
  }
}
