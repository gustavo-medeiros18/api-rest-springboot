package br.com.erudio.mapper.custom;

import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {
  public PersonVOV2 convertEntityToVo(Person entity) {
    PersonVOV2 vo = new PersonVOV2();

    vo.setId(entity.getId());
    vo.setAddress(entity.getAddress());

    /*
      Just to simulate a birthDay value, since
      we don't have this column in the database.
     */
    vo.setBirthDay(new Date());

    vo.setFirstName(entity.getFirstName());
    vo.setLastName(entity.getLastName());
    vo.setGender(entity.getGender());

    return vo;
  }

  public Person convertVoToEntity(PersonVOV2 vo) {
    Person entity = new Person();

    entity.setId(vo.getId());
    entity.setAddress(vo.getAddress());
    entity.setFirstName(entity.getFirstName());
    entity.setLastName(entity.getLastName());
    entity.setGender(entity.getGender());

    return entity;
  }
}
