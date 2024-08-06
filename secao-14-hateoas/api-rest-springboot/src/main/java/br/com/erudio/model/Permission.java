package br.com.erudio.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "permission")
/**
 * The GrantedAuthority interface is used to represent an authority granted to
 * an Authentication object. An authority is used to determine whether a user
 * is allowed to perform a given action or access a given resource.
 *
 * It's necessary to implement this interface to use Spring Security.
 */
public class Permission implements GrantedAuthority, Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String description;

  public Permission() {

  }

  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Permission that = (Permission) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(description, that.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, description);
  }

  @Override
  public String getAuthority() {
    return this.description;
  }
}
