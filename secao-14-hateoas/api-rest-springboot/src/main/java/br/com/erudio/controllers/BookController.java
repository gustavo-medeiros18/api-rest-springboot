package br.com.erudio.controllers;

import br.com.erudio.model.Book;
import br.com.erudio.services.BookServices;
import br.com.erudio.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
public class BookController {
  @Autowired
  private BookServices service;

  @GetMapping(
      value = "/{id}",
      produces = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      }
  )
  public Book findById(@PathVariable(value = "id") Long id) {
    return service.findById(id);
  }

  @GetMapping(
      produces = MediaType.APLLICATION_JSON
  )
  public List<Book> findAll() {
    return service.findAll();
  }

  @PostMapping(
      produces = MediaType.APLLICATION_JSON,
      consumes = MediaType.APLLICATION_JSON
  )
  public Book create(@RequestBody Book book) {
    return service.create(book);
  }

  @PutMapping(
      produces = MediaType.APLLICATION_JSON,
      consumes = MediaType.APLLICATION_JSON
  )
  public Book update(@RequestBody Book book) {
    return service.update(book);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable(value = "id") Long id) {
    service.delete(id);
  }
}
