package br.com.erudio.config;

import br.com.erudio.security.jwt.JwtTokenFilter;
import br.com.erudio.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * The @EnableWebSecurity annotation enables Spring Security's web security support
 * and provides the Spring MVC integration. It allows customization of web security
 * by extending WebSecurityConfigurerAdapter and overriding its methods.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {
  @Autowired
  private JwtTokenProvider tokenProvider;

  /**
   * The PasswordEncoder method defines a bean that provides password encoding
   * functionality. It is used to encode passwords for storage and to verify
   * encoded passwords during authentication.
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    /**
     * The encoders map holds different types of password encoders. It is instantiated
     * as a hash map to provide efficient storage and retrieval of encoders by their
     * names (keys).
     */
    Map<String, PasswordEncoder> encoders = new HashMap<>();

    /**
     * The pbkdf2Encoder object is an instance of Pbkdf2PasswordEncoder, which uses
     * the PBKDF2 algorithm with HMAC-SHA256 for password hashing. It is used to
     * encode passwords with a high level of security.
     */
    Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder(
        "",
        8,
        185000,
        Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
    );
    encoders.put("pbkdf2", pbkdf2Encoder);

    /**
     * The passwordEncoder object is an instance of DelegatingPasswordEncoder, which
     * delegates password encoding to one of the encoders in the encoders map based
     * on a prefix in the encoded password. It allows for multiple encoding strategies.
     */
    DelegatingPasswordEncoder passwordEncoder =
        new DelegatingPasswordEncoder("pbkdf2", encoders);

    /**
     * The setDefaultPasswordEncoderForMatches method sets the default password encoder
     * to be used when no prefix is found in the encoded password. In this case, the
     * pbkdf2Encoder object is passed as an argument to ensure PBKDF2 is used by default.
     */
    passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);

    return passwordEncoder;
  }

  /**
   * The authenticationManagerBean method defines a bean that provides an
   * AuthenticationManager instance. It is used to handle authentication requests
   * and to manage the authentication process.
   */
  @Bean
  AuthenticationManager authenticationManagerBean(
      AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  /**
   * The securityFilterChain method defines a bean that configures the security filter
   * chain. It sets up the security configuration for HTTP requests, including disabling
   * basic authentication and CSRF protection, adding a custom JWT filter, setting the
   * session management policy to stateless, and defining authorization rules for
   * different request matchers.
   */
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);

    /**
     * This configuration disables basic authentication and CSRF protection, adds the
     * custom JWT filter before the UsernamePasswordAuthenticationFilter, sets the
     * session management policy to stateless, and defines authorization rules for
     * different request matchers. It allows unauthenticated access to certain endpoints
     * and requires authentication for others.
     */
    return http
        .httpBasic(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            authorizeHttpRequests -> authorizeHttpRequests
                .requestMatchers(
                    "/auth/signin",
                    "/auth/refresh/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()
                .requestMatchers("/api/**").authenticated()
                .requestMatchers("/users").denyAll()
        )
        .cors(cors -> {})
        .build();
  }
}
