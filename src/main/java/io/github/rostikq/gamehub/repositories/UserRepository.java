package io.github.rostikq.gamehub.repositories;

import io.github.rostikq.gamehub.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    public boolean existsByUsername(String username);
}
