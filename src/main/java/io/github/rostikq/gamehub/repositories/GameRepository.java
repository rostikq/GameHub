package io.github.rostikq.gamehub.repositories;

import io.github.rostikq.gamehub.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
