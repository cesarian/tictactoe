package nl.cesar.tictactoe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.util.GameState;

@Repository
public interface GameRepository extends CrudRepository<Game, Long>{
	Optional<Game> findById(Long id);
	List<Game> findByGameState(GameState gameState);
	List<Game> findAll();
}
