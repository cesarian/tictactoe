package nl.cesar.tictactoe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.cesar.tictactoe.data.transfer.model.request.MoveRequestModel;
import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.domain.Player;
import nl.cesar.tictactoe.game.GameError;
import nl.cesar.tictactoe.game.GameLogic;
import nl.cesar.tictactoe.game.GameValidation;
import nl.cesar.tictactoe.repository.GameRepository;
import nl.cesar.tictactoe.repository.PlayerRepository;
import nl.cesar.tictactoe.service.exception.GameException;
import nl.cesar.tictactoe.util.GameState;
import nl.cesar.tictactoe.util.MoveUtil;

@Service
@Transactional
public class GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private MoveUtil moveUtil;
	
	@Autowired
	private GameLogic gameLogic;
	
	@Autowired
	private GameValidation gameValidation;
	
	private Player getLoggedInPlayer(String loggedInUser) throws UsernameNotFoundException{
		Optional<Player> loggedInPlayer = playerRepository.findByUsername(loggedInUser);
		loggedInPlayer.orElseThrow(() -> new UsernameNotFoundException(GameError.USER_NOT_FOUND + " Username: " + loggedInUser));
		
		return loggedInPlayer.get();
	}
	
	private Game getGame(Long gameId) throws GameException {
		Optional<Game> game = gameRepository.findById(gameId);
		game.orElseThrow(() -> new GameException(GameError.GAME_NOT_FOUND + " Game ID: " + gameId));
		
		return game.get();
	}
	
	public List<Game> getOpenGames() {
		List<Game> games = gameRepository.findByGameState(GameState.OPEN);
		return games;
	}
	
	public List<Game> getAllGames() {
		return gameRepository.findAll();
	}

	public Game createNewGame(String loggedInUser) throws UsernameNotFoundException, GameException {
		Player loggedInPlayer;
		Game game;
		
		try {
			loggedInPlayer = getLoggedInPlayer(loggedInUser);
			game = new Game(loggedInPlayer.getId());
			
			gameRepository.save(game);
		} catch (UsernameNotFoundException e) {
			throw e;
		} catch(Exception e) {
			throw new GameException(e.getMessage(), e);
		}
		
		return game;
	}
	
	public Game joinGame(String loggedInUser, Long gameId) throws UsernameNotFoundException, GameException{
		Player loggedInPlayer;
		Game game;
		
		try {
			loggedInPlayer = getLoggedInPlayer(loggedInUser);
			game = getGame(gameId);
			
			String errorMessage = gameValidation.validateJoinGame(game, loggedInPlayer);
			
			if(errorMessage != null) {
				throw new GameException(errorMessage);
			}
			
			game.setPlayer2Id(loggedInPlayer.getId());
			game.setGameState(GameState.INPROGRESS);
			
			gameRepository.save(game);
		} catch (UsernameNotFoundException e) {
			throw e;
		} catch (GameException e) {
			throw e;
		} catch (Exception e) {
			throw new GameException(e.getMessage(), e);
		}
		
		return game;
	}
	
	public void leaveGame(String loggedInUser, Long gameId) throws UsernameNotFoundException, GameException {
		Player loggedInPlayer;
		Game game;
		
		try {
			loggedInPlayer = getLoggedInPlayer(loggedInUser);
			game = getGame(gameId);
			
			String errorMessage = gameValidation.validateLeaveGame(game, loggedInPlayer);
			
			if(errorMessage != null) {
				throw new GameException(errorMessage);
			}
			
			gameLogic.updateGameStateAfterPlayerLeave(game, loggedInPlayer);
			
			gameRepository.save(game);
		} catch (UsernameNotFoundException e) {
			throw e;
		} catch (GameException e) {
			throw e;
		}
	}	
	
	public Game makeMove(String loggedInUser, MoveRequestModel moveRequestModel) throws UsernameNotFoundException, GameException {
		Player loggedInPlayer;
		Game game;
		
		try {
			loggedInPlayer = getLoggedInPlayer(loggedInUser);
			game = getGame(moveRequestModel.getGameId());
			
			Character symbol = moveRequestModel.getSymbol();
			int position = moveRequestModel.getPosition();
			
			String errorMessage = gameValidation.validateMove(game, symbol, position, loggedInPlayer);
			
			if(errorMessage != null) {
				throw new GameException(errorMessage);
			}
			
			moveUtil.setSymbolInPosition(game, position, symbol);
			gameLogic.updateGameStateAfterMove(game, position, loggedInPlayer);
			
			gameRepository.save(game);
		} catch (UsernameNotFoundException e) {
			throw e;
		} catch (GameException e) {
			throw e;
		} catch(Exception e) {
			throw new GameException(e.getMessage(), e);
		}
		
		return game;
	}

}
