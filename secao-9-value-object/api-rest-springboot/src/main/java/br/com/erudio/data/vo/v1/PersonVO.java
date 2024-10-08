//DOCS
package br.com.erudio.data.vo.v1;

import java.io.Serializable;
import java.util.Objects;


public class PersonVO implements Serializable {
  private Long id;
  private String firstName;
  private String lastName;
  private String address;
  private String gender;

  public PersonVO() {

  }

  public String getAddress() {
    return address;
  }

  public Long getId() {
    return id;
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

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PersonVO person = (PersonVO) o;
    return Objects.equals(id, person.id) &&
        Objects.equals(firstName, person.firstName) &&
        Objects.equals(lastName, person.lastName) &&
        Objects.equals(address, person.address) &&
        Objects.equals(gender, person.gender);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, gender);
  }
}
