package br.com.erudio.services;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {
  private Logger logger = Logger.getLogger(
      PersonServices.class.getName()
  );

  @Autowired
  private PersonRepository repository;

  public List<Person> findAll() {
    logger.info("Finding all people!");

    return repository.findAll();
  }

  public Person findById(Long id) {
    logger.info("Finding one person!");

    return repository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("No records found for this ID!")
    );
  }

  public Person createPerson(Person person) {
    logger.info("Creating one person!");

    return repository.save(person);
  }

  public Person updatePerson(Person person) {
    logger.info("Updating one person!");

    /**
     * The var keyword is used to declare a variable which is
     * not explicitly typed. In other words, the type of the
     * variable will be determined by the compiler (type
     * inference), just like TypeScript.
     */
    var entity = repository.findById(person.getId()).orElseThrow(() ->
        new ResourceNotFoundException("No records found for this ID!")
    );

    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    return repository.save(entity);
  }

  public void delete(Long id) {
    logger.info("Deleting one person!");

    var entity = repository.findById(id).orElseThrow(() ->
        new ResourceNotFoundException("No records found for this ID!")
    );

    repository.delete(entity);
  }
}
