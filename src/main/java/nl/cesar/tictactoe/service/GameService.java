package nl.cesar.tictactoe.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.cesar.tictactoe.data.transfer.model.request.GameJoinRequestModel;
import nl.cesar.tictactoe.data.transfer.model.request.GameStartRequestModel;
import nl.cesar.tictactoe.data.transfer.model.request.MoveRequestModel;
import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.repository.GameRepository;
import nl.cesar.tictactoe.util.GameState;

@Service
@Transactional
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;

	public Game createNewGame(GameStartRequestModel gameStartRequestModel) throws Exception {
		Game game = new Game(gameStartRequestModel.getPlayer1Id());
		
		try {
			gameRepository.save(game);
		} catch(Exception e) {
			throw e;
		}
		
		return game;
	}
	
	public Game joinGame(GameJoinRequestModel gameJoinRequestModel) throws Exception {
		Optional<Game> game = gameRepository.findById(gameJoinRequestModel.getGameId());
		game.orElseThrow(() -> new Exception("Game not found " + gameJoinRequestModel.getGameId()));
		
		Game g = game.get();
		g.setPlayer2Id(gameJoinRequestModel.getPlayerId());
		
		try {
			gameRepository.save(g);
		} catch(Exception e) {
			throw e;
		}
		
		return g;
	}
	
	public List<Game> getOpenGames(Long activePlayerId) {
		List<Game> games = gameRepository.findByGameState(GameState.OPEN);
		List<Game> filteredGames = new ArrayList<Game>();
		
		for(Game g : games) {
			if(g.getPlayer1Id() != activePlayerId) {
				filteredGames.add(g);
			}
		}
		
		return filteredGames;
	}
	
	public Game makeMove(MoveRequestModel moveRequestModel) throws Exception{
		Optional<Game> game = gameRepository.findById(moveRequestModel.getGameId());
		game.orElseThrow(() -> new Exception("Game not found " + moveRequestModel.getGameId()));
		
		Game g = game.get();
		
		try {
			gameRepository.save(g);
		} catch(Exception e) {
			throw e;
		}
		
		return g;
	}

}
