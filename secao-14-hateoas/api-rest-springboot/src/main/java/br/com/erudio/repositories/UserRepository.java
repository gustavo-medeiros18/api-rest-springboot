package br.com.erudio.repositories;

import br.com.erudio.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Here, it was necessary to create a custom query to find a user by its username.
   * For this, we can use the Query annotation and pass the query as a parameter.
   * <p>
   * It's important to remember that the query here is not a SQL query, but a JPQL
   * query. So we need to use the entity name and the field name, not the table name
   * and the column name.
   */
  @Query("SELECT u FROM User u WHERE u.userName =: userName")
  User findByUsername(@Param("userName") String userName);
}
