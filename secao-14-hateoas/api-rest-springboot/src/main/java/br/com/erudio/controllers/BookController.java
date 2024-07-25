package br.com.erudio.controllers;

import br.com.erudio.util.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book/v1")
public class BookController {
  @GetMapping(
      produces = MediaType.APLLICATION_JSON
  )
  public String findAll() {
    return "API REST Spring Boot";
  }
}
