package br.com.erudio.services;

import br.com.erudio.controllers.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {
  private Logger logger = Logger.getLogger(
      PersonServices.class.getName()
  );

  @Autowired
  private PersonRepository repository;

  public List<PersonVO> findAll() {
    logger.info("Finding all people!");

    /*
      The DozerMapper class is a utility class that provides
      methods to convert objects from one type to another.
      In our application, it's being used to convert the objects
      from entities to VOs and vice-versa.
     */
    List<PersonVO> voList = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
    voList.forEach(
        p -> p.add(linkTo(methodOn(PersonController.class)
            .findById(p.getKey()))
            .withSelfRel())
    );

    return voList;
  }

  public PersonVO findById(Long id) {
    logger.info("Finding one person!");

    var entity = repository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("No records found for this ID!")
    );

    PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
    /*
      Adds a HATEOAS link to the PersonVO object, enabling the API client
      to directly navigate to the Person resource identified by the provided ID.

      The method linkTo(methodOn(PersonController.class).findById(id)) creates a link to
      the findById method of PersonController, using the Person's ID as a parameter.

      The method withSelfRel() specifies that this is a 'self' link, i.e., a link to
      the Person resource itself. This enriches the API response with navigation information
      following HATEOAS principles, facilitating a more discoverable and dynamic interaction
      with the API.
     */
    vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

    return vo;
  }

  public PersonVO create(PersonVO person) {
    logger.info("Creating one person!");

    var entity = DozerMapper.parseObject(person, Person.class);

    PersonVO vo = DozerMapper.parseObject(
        repository.save(entity),
        PersonVO.class
    );

    vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

    return vo;
  }

  public PersonVO update(PersonVO person) {
    logger.info("Updating one person!");

    /**
     * The var keyword is used to declare a variable which is
     * not explicitly typed. In other words, the type of the
     * variable will be determined by the compiler (type
     * inference), just like TypeScript.
     */
    var entity = repository.findById(person.getKey()).orElseThrow(() ->
        new ResourceNotFoundException("No records found for this ID!")
    );

    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    return DozerMapper.parseObject(
        repository.save(entity),
        PersonVO.class
    );
  }

  public void delete(Long id) {
    logger.info("Deleting one person!");

    var entity = repository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("No records found for this ID!")
    );

    repository.delete(entity);
  }
}
