package soft_uni.gameservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import soft_uni.gameservice.entities.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}
