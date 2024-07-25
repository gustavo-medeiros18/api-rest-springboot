package br.com.erudio.services;

import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class BookServices {
  private Logger logger = Logger.getLogger(
      BookServices.class.getName()
  );

  @Autowired
  private BookRepository repository;

  public List<Book> findAll() {
    logger.info("Finding all books!");

    return repository.findAll();
  }

  public Book findById(Long id) {
    logger.info("Finding book by id: " + id);

    return repository.findById(id).orElse(null);
  }

  public Book create(Book book) {
    logger.info("Creating book!");

    return repository.save(book);
  }
}
