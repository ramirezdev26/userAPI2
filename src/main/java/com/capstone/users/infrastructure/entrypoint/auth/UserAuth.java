package com.capstone.users.infrastructure.entrypoint.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuth implements UserDetails {

  private String id;
  private String name;
  private String login;
  private String password;

  /**
   * Returns an empty collection of granted authorities.
   *
   * @return an empty collection of GrantedAuthority objects
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  /**
   * Retrieves the password for the user.
   *
   * @return the password for the user
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Retrieves the username of the user.
   *
   * @return the username of the user
   */
  @Override
  public String getUsername() {
    return login;
  }

}
