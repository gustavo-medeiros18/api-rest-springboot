package br.com.erudio.integrationtests.vo;

import java.io.Serializable;
import java.util.Objects;

/**
 * It's necessary to create this alternative PersonVO class because the original one has
 * hateoas-related annotations and fields that are not necessary for the integration tests.
 * Since the hateoas feature is already tested in the unit tests, we can use this simpler
 * version of the PersonVO class for the integration tests.
 */
public class PersonVO implements Serializable {
  private Long Id;
  private String firstName;
  private String lastName;
  private String address;
  private String gender;

  public PersonVO() {

  }

  public Long getId() {
    return Id;
  }

  public String getAddress() {
    return address;
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

  public void setId(Long Id) {
    this.Id = Id;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PersonVO personVO = (PersonVO) o;
    return Objects.equals(Id, personVO.Id) &&
        Objects.equals(firstName, personVO.firstName) &&
        Objects.equals(lastName, personVO.lastName) &&
        Objects.equals(address, personVO.address) &&
        Objects.equals(gender, personVO.gender);
  }

  @Override
  public int hashCode() {
    return Objects.hash(Id, firstName, lastName, address, gender);
  }
}
