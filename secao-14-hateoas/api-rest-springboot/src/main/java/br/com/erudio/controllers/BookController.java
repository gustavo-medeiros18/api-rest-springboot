package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.BookVO;
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
  public BookVO findById(@PathVariable(value = "id") Long id) {
    return service.findById(id);
  }

  @GetMapping(
      produces = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      }
  )
  public List<BookVO> findAll() {
    return service.findAll();
  }

  @PostMapping(
      consumes = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      },
      produces = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      }
  )
  public BookVO create(@RequestBody BookVO book) {
    return service.create(book);
  }

  @PutMapping(
      consumes = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      },
      produces = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      }
  )
  public BookVO update(@RequestBody BookVO book) {
    return service.update(book);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable(value = "id") Long id) {
    service.delete(id);
  }
}
