package br.com.erudio.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
/**
 * The UserDetails interface is used to represent a user in the system. It's
 * necessary to implement this interface to use Spring Security.
 */
public class User implements UserDetails, Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_name", unique = true)
  private String userName;

  @Column(name = "full_name")
  private String fullName;

  @Column(name = "password")
  private String password;

  @Column(name = "account_non_expired")
  private Boolean accountNonExpired;

  @Column(name = "account_non_locked")
  private Boolean accountNonLocked;

  @Column(name = "credentials_non_expired")
  private Boolean credentialsNonExpired;

  @Column(name = "enabled")
  private Boolean enabled;

  /**
   * The FetchType.EAGER strategy is used to load the permissions of a user when
   * the user is loaded.
   * <p>
   * The oposite is FetchType.LAZY, which is used to load the permissions of a
   * user only when the permissions are accessed.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_permission",
      joinColumns = @JoinColumn(name = "id_user"),
      inverseJoinColumns = @JoinColumn(name = "id_permission")
  )
  private List<Permission> permissions;

  public User() {

  }

  public List<String> getRoles() {
    List<String> roles = new ArrayList<>();
    for (Permission permission : this.permissions)
      roles.add(permission.getDescription());

    return roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.permissions;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;
    return Objects.equals(id, user.id) &&
        Objects.equals(userName, user.userName) &&
        Objects.equals(fullName, user.fullName) &&
        Objects.equals(password, user.password) &&
        Objects.equals(accountNonExpired, user.accountNonExpired) &&
        Objects.equals(accountNonLocked, user.accountNonLocked) &&
        Objects.equals(credentialsNonExpired, user.credentialsNonExpired) &&
        Objects.equals(enabled, user.enabled) &&
        Objects.equals(permissions, user.permissions);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id,
        userName,
        fullName,
        password,
        accountNonExpired,
        accountNonLocked,
        credentialsNonExpired,
        enabled,
        permissions
    );
  }
}
