package gr.hua.dit.ds.it21811_it22056.repositories;

import gr.hua.dit.ds.it21811_it22056.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);  // This finds a user by their 'email' field
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    List<User> findByVerified(int verified);

}