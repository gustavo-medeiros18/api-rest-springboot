package br.com.erudio.controllers;

import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/person")
public class PersonController {
  @Autowired
  private PersonServices service;

  @GetMapping(
      value = "/{id}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Person findById(
      @PathVariable(value = "id") Long id
  ) {
    return service.findById(id);
  }

  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public List<Person> findAll() {
    return service.findAll();
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Person createPerson(
      @RequestBody Person person
  ) {
    return service.createPerson(person);
  }

  @PutMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Person updatePerson(
      @RequestBody Person person
  ) {
    return service.updatePerson(person);
  }

  @DeleteMapping(
      value = "/{id}"
  )
  public void delete(
      @PathVariable(value = "id") Long id
  ) {
    service.delete(id);
  }
}
