//DOCS
package br.com.erudio.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.Objects;

/**
 * The JsonPropertyOrder annotation is used to define the order
 * of the properties in the JSON representation of the object.
 * More specifically, it is used to define the order of the properties
 * that will be sent to the client when the object is serialized.
 */
@JsonPropertyOrder({"id", "address", "firstName", "lastName", "gender"})
public class PersonVO implements Serializable {
  private Long id;

  /**
   * The JsonProperty annotation is used to define the name of the property
   * in the JSON representation of the object that will be sent to the client.
   */
  @JsonProperty("first_name")
  private String firstName;

  @JsonProperty("last_name")
  private String lastName;
  private String address;

  /**
   * The JsonIgnore annotation is used to ignore a property in the JSON representation
   * of the object that will be sent to the client.
   */
  @JsonIgnore
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
