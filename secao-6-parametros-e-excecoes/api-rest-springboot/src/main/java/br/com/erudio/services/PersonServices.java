package br.com.erudio.services;

import br.com.erudio.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {
  private final AtomicLong counter = new AtomicLong();
  private Logger logger = Logger.getLogger(
      PersonServices.class.getName()
  );

  public List<Person> findAll() {
    logger.info("Finding all people!");

    List<Person> persons = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      Person person = mockPerson(i);
      persons.add(person);
    }

    return persons;
  }

  public Person findById(String id) {
    logger.info("Finding one person!");

    Person person = new Person();

    person.setId(counter.incrementAndGet());
    person.setFirstName("Gustavo");
    person.setLastName("Medeiros");
    person.setAddress("Sobral - Ceara - Brasil");
    person.setGender("Male");

    return person;
  }

  private Person mockPerson(int i) {
    Person person = new Person();

    person.setId(counter.incrementAndGet());
    person.setFirstName("Person Name " + counter.get());
    person.setLastName("Last Name " + counter.get());
    person.setAddress("Some Address in Brazil " + counter.get());
    person.setGender("Male");

    return person;
  }
}
