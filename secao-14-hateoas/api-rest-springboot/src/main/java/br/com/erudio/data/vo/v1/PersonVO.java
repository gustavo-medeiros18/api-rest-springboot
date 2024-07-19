//DOCS
package br.com.erudio.data.vo.v1;

import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Objects;

/**
 * Our VO needs to extend the RepresentationModel class from the Spring HATEOAS
 * library. This class is used to add links to a object that will come on
 * the response, which is a requirement for HATEOAS.
 */
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {
  /**
   * This attribute needs to be called 'key' instead of 'id' because the
   * RepresentationModel class already has an 'id' attribute.
   * <p>
   * It's necessary to use the @Mapping annotation to map the "key" attribute
   * from VO as the "id" attribute from the entity, since it's reccomended to
   * use same attribute names in the entity and VO classes. But, when this is
   * not possible, just use the @Mapping annotation to map the attributes.
   */
  @Mapping("id")
  private Long key;
  private String firstName;
  private String lastName;
  private String address;
  private String gender;

  public PersonVO() {

  }

  public String getAddress() {
    return address;
  }

  public Long getKey() {
    return key;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setKey(Long key) {
    this.key = key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PersonVO person = (PersonVO) o;
    return Objects.equals(key, person.key) &&
        Objects.equals(firstName, person.firstName) &&
        Objects.equals(lastName, person.lastName) &&
        Objects.equals(address, person.address) &&
        Objects.equals(gender, person.gender);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, firstName, lastName, address, gender);
  }
}
