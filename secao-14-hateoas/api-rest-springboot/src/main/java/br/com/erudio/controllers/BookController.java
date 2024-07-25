package br.com.erudio.controllers;

import br.com.erudio.model.Book;
import br.com.erudio.services.BookServices;
import br.com.erudio.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book/v1")
public class BookController {
  @Autowired
  private BookServices service;

  @GetMapping(
      produces = MediaType.APLLICATION_JSON
  )
  public List<Book> findAll() {
    return service.findAll();
  }
}
