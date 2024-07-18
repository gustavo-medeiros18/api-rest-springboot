package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/person/v1")
public class PersonController {
  @Autowired
  private PersonServices service;

  @GetMapping(
      value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public PersonVO findById(
      @PathVariable(value = "id") Long id
  ) {
    return service.findById(id);
  }

  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public List<PersonVO> findAll() {
    return service.findAll();
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public PersonVO createPerson(
      @RequestBody PersonVO person
  ) {
    return service.create(person);
  }

  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public PersonVO updatePerson(
      @RequestBody PersonVO person
  ) {
    return service.update(person);
  }

  @DeleteMapping(
      value = "/{id}"
  )
  public ResponseEntity<Void> delete(
      @PathVariable(value = "id") Long id
  ) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
