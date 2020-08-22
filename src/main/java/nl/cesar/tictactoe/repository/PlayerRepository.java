package nl.cesar.tictactoe.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nl.cesar.tictactoe.domain.Player;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer>{

	Optional<Player> findByUsername(String userName);
	Optional<Player> findById(Integer id);

}
