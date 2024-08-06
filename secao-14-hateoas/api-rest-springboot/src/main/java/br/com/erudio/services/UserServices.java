package br.com.erudio.services;

import br.com.erudio.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService {
  private Logger logger = Logger.getLogger(
      UserServices.class.getName()
  );

  @Autowired
  private UserRepository repository;

  /**
   * Here we created a constructor to receive the repository.
   * This was made to ensure constructor injection.
   * <p>
   * If we don't do this, Spring will use setter injection, which
   * has no problem at all, but in this type of injection there is the
   * possibility of the repository being null, since it is not mandatory
   * for Spring to set it when instantiating the class, causing the risk
   * of a NullPointerException when trying to use an application feature
   * that needs this repository.
   * <p>
   * By creating a constructor to receive the repository (considering that
   * this field must be Autowired), we guarantee that the repository will
   * be instantiated when the service class is instantiated. If the repository
   * is not instantiated, Spring will throw an exception and won't start the
   * application.
   */
  public UserServices(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("Finding user by username: " + username);

    var user = repository.findByUsername(username);

    if (user != null)
      return user;
    else
      throw new UsernameNotFoundException("Username " + username + " not found");
  }
}
