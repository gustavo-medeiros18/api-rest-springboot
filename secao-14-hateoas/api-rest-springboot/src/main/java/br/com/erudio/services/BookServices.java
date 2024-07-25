package br.com.erudio.services;

import br.com.erudio.controllers.BookController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Book;
import br.com.erudio.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {
  private Logger logger = Logger.getLogger(
      BookServices.class.getName()
  );

  @Autowired
  private BookRepository repository;

  public List<BookVO> findAll() {
    logger.info("Finding all books!");

    List<BookVO> voList = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
    voList.forEach(
        p -> p.add(linkTo(methodOn(BookController.class)
            .findById(p.getKey()))
            .withSelfRel())
    );

    return voList;
  }

  public Book findById(Long id) {
    logger.info("Finding book by id: " + id);

    return repository.findById(id).orElse(null);
  }

  public Book create(Book book) {
    logger.info("Creating book!");

    return repository.save(book);
  }

  public Book update(Book book) {
    logger.info("Updating book!");

    var entity = repository.findById(book.getId()).orElse(null);
    if (entity != null) {
      entity.setAuthor(book.getAuthor());
      entity.setLaunchDate(book.getLaunchDate());
      entity.setPrice(book.getPrice());
      entity.setTitle(book.getTitle());

      return repository.save(entity);
    }

    return null;
  }

  public void delete(Long id) {
    logger.info("Deleting book by id: " + id);

    var entity = repository.findById(id).orElse(null);
    if (entity != null) repository.delete(entity);
  }
}
