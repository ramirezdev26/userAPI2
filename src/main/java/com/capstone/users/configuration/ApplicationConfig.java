package com.capstone.users.configuration;

import com.capstone.users.domain.service.UserService;
import com.capstone.users.infrastructure.entrypoint.auth.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final UserService userService;

  /**
   * Returns the authentication manager configured by the given AuthenticationConfiguration.
   *
   * @param  config  the AuthenticationConfiguration used to configure the authentication manager
   * @return         the authentication manager configured by the given AuthenticationConfiguration
   * @throws Exception if an error occurs while retrieving the authentication manager
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  /**
   * Creates and configures an instance of the DaoAuthenticationProvider bean.
   *
   * @return an instance of the DaoAuthenticationProvider bean
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  /**
   * Returns a new instance of the BCryptPasswordEncoder bean.
   *
   * @return a new instance of the BCryptPasswordEncoder bean
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Returns a UserDetailsService bean that retrieves user details by username.
   *
   * @param  username  the username of the user to retrieve details for
   * @return           a UserDetails object representing the user, or throws a UsernameNotFoundException if the user is not found
   * @throws UsernameNotFoundException if the user is not found
   */
  @Bean
  public UserDetailsService userDetailService() {
    return username ->
        userService.findByLogin(username)
            .map(user -> UserAuth.builder()
                .id(user.getId())
                .name(user.getName())
                .login(user.getLogin())
                .password(user.getPassword())
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("not found"));
  }

}
