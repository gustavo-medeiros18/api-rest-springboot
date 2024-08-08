package br.com.erudio.security.jwt;

import br.com.erudio.exceptions.InvalidJwtAuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

/**
 * The GenericFilterBean class is a base class for filter implementations.
 * In this case, JwtTokenFilter extends GenericFilterBean to implement
 * a filter that checks for a JWT token.
 */
@Service
public class JwtTokenFilter extends GenericFilterBean {
  @Autowired
  private JwtTokenProvider tokenProvider;

  public JwtTokenFilter(JwtTokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  /**
   * The doFilter method is called by the container each time a request/response
   * pair is passed through the chain due to a client request for a resource at the end of the chain.
   * <p>
   * It executes a filter to check for a JWT token in the request header, and if the token is valid,
   */
  @Override
  public void doFilter(
      ServletRequest servletRequest,
      ServletResponse servletResponse,
      FilterChain filterChain
  )
      throws IOException, ServletException {
    String token = tokenProvider.resolveToken((HttpServletRequest) servletRequest);

    try {
      if (token != null && tokenProvider.validateToken(token)) {
        Authentication auth = tokenProvider.getAuthentication(token);

        if (auth != null)
        /**
         * The SecurityContextHolder is a helper class that provides access to the security context.
         * The security context is a thread-local object that holds details of the principal currently
         * interacting with the application.
         *
         * Here, we set the Authentication object in the SecurityContextHolder to authenticate the user.
         */
          SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (InvalidJwtAuthenticationException e) {
      throw new RuntimeException(e);
    }

    /**
     * The filterChain object is used to invoke the next filter in the chain.
     * If the current filter is the last filter in the chain, the filterChain object
     * invokes the resource at the end of the chain.
     */
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
