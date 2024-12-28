package mk.finki.ukim.mk.lab3.repository;

import mk.finki.ukim.mk.lab3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findUserByUsernameAndPassword(String username, String password);
}
