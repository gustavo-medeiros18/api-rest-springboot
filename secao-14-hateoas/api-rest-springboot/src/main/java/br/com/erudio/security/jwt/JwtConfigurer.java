package br.com.erudio.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for configuring the JwtTokenFilter in the Spring Security filter chain.
 * It needs to extend the SecurityConfigurerAdapter class and override the configure method.
 * <p>
 * The SecurityConfigurerAdapter class is a generic class that receives the DefaultSecurityFilterChain
 * and HttpSecurity as parameters. It serves as a base class for all security configurers in Spring Security.
 */
@Service
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
  @Autowired
  private final JwtTokenProvider jwtTokenProvider;

  public JwtConfigurer(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  /**
   * The configure method is responsible for adding the JwtTokenFilter to the Spring
   * Security filter chain.
   */
  @Override
  public void configure(HttpSecurity http) {
    JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProvider);

    /**
     * The UsernamePasswordAuthenticationFilter is the filter responsible for authenticating the user.
     * It is the filter that receives the user credentials and authenticates the user.
     * <p>
     * The customFilter is the JwtTokenFilter that we created to validate the JWT token.
     * <p>
     * Here, we are adding the JwtTokenFilter before the UsernamePasswordAuthenticationFilter.
     * This way, the JwtTokenFilter will be executed before the UsernamePasswordAuthenticationFilter.
     */
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
