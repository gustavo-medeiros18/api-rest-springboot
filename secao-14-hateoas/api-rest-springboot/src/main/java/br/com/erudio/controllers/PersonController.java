package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.erudio.util.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/person/v1")
public class PersonController {
  @Autowired
  private PersonServices service;

  private static final String YAML_MEDIA_TYPE = "application/x-yaml";

  @GetMapping(
      value = "/{id}",
      produces = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      }
  )
  public PersonVO findById(
      @PathVariable(value = "id") Long id
  ) {
    return service.findById(id);
  }

  @GetMapping(
      produces = {
          MediaType.APLLICATION_JSON,
          MediaType.APLLICATION_XML,
          MediaType.APPLICATION_YML
      }
  )
  public List<PersonVO> findAll() {
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
  public PersonVO createPerson(
      @RequestBody PersonVO person
  ) {
    return service.create(person);
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