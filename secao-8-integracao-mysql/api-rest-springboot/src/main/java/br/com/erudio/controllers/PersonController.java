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

  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Person findById(
      @PathVariable(value = "id") String id
  ) {
    return service.findById(id);
  }

  @RequestMapping(
      method = RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public List<Person> findAll() {
    return service.findAll();
  }

  @RequestMapping(
      method = RequestMethod.POST,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Person createPerson(
      @RequestBody Person person
  ) {
    return service.createPerson(person);
  }

  @RequestMapping(
      method = RequestMethod.PUT,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Person updatePerson(
      @RequestBody Person person
  ) {
    return service.updatePerson(person);
  }

  @RequestMapping(
      value = "/{id}",
      method = RequestMethod.DELETE
  )
  public void delete(
      @PathVariable(value = "id") String id
  ) {
    service.delete(id);
  }
}