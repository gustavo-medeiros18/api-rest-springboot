package br.com.erudio.services;

import br.com.erudio.controllers.BookController;
import br.com.erudio.data.vo.v1.BookVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
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

  public BookVO findById(Long id) {
    logger.info("Finding book by id: " + id);

    var entity = repository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("No records found for this ID!")
    );

    BookVO vo = DozerMapper.parseObject(entity, BookVO.class);
    vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());

    return vo;
  }

  public BookVO create(BookVO book) {
    if (book == null)
      throw new RequiredObjectIsNullException();

    logger.info("Creating book!");

    var entity = DozerMapper.parseObject(book, Book.class);
    BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);

    vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
    return vo;
  }

  public BookVO update(BookVO book) {
    if (book == null)
      throw new RequiredObjectIsNullException();

    logger.info("Updating book!");

    var entity = repository.findById(book.getKey()).orElseThrow(() ->
        new ResourceNotFoundException("No records found for this ID!")
    );

    if (entity != null) {
      entity.setAuthor(book.getAuthor());
      entity.setLaunchDate(book.getLaunchDate());
      entity.setPrice(book.getPrice());
      entity.setTitle(book.getTitle());

      BookVO vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
      vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

      return vo;
    }

    return null;
  }

  public void delete(Long id) {
    logger.info("Deleting book by id: " + id);

    var entity = repository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("No records found for this ID!")
    );
    if (entity != null) repository.delete(entity);
  }
}
