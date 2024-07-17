package br.com.erudio.data.vo.v2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


public class PersonVOV2 implements Serializable {
  private Long id;
  private String firstName;
  private String lastName;
  private String address;
  private String gender;

  private Date birthDay;

  public PersonVOV2() {

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

  public Date getBirthDay() {
    return birthDay;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setBirthDay(Date birthDay) {
    this.birthDay = birthDay;
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
    PersonVOV2 that = (PersonVOV2) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(firstName, that.firstName) &&
        Objects.equals(lastName, that.lastName) &&
        Objects.equals(address, that.address) &&
        Objects.equals(gender, that.gender) &&
        Objects.equals(birthDay, that.birthDay);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, address, gender, birthDay);
  }
}
