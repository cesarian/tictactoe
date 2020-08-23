package nl.cesar.tictactoe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.cesar.tictactoe.data.transfer.model.request.GameJoinRequestModel;
import nl.cesar.tictactoe.data.transfer.model.request.MoveRequestModel;
import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.domain.Player;
import nl.cesar.tictactoe.game.GameLogic;
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
	GameLogic gameLogic;
	
	private Player getLoggedInPlayer(String loggedInUser) throws UsernameNotFoundException{
		Optional<Player> loggedInPlayer = playerRepository.findByUsername(loggedInUser);
		loggedInPlayer.orElseThrow(() -> new UsernameNotFoundException("User not found: " + loggedInUser));
		
		return loggedInPlayer.get();
	}

	public Game createNewGame(String loggedInUser) throws UsernameNotFoundException, Exception {
		Player loggedInPlayer;
		
		try {
			loggedInPlayer = getLoggedInPlayer(loggedInUser);
		} catch (UsernameNotFoundException e) {
			throw e;
		}
		
		Game game = new Game(loggedInPlayer.getId());
		
		try {
			gameRepository.save(game);
		} catch(Exception e) {
			throw e;
		}
		
		return game;
	}
	
	public Game joinGame(String loggedInUser, GameJoinRequestModel gameJoinRequestModel) throws GameException, Exception {
		Player loggedInPlayer;
		
		try {
			loggedInPlayer = getLoggedInPlayer(loggedInUser);
		} catch (UsernameNotFoundException e) {
			throw e;
		}
		
		Optional<Game> game = gameRepository.findById(gameJoinRequestModel.getGameId());
		game.orElseThrow(() -> new Exception("Game not found " + gameJoinRequestModel.getGameId()));
		
		Game g = game.get();
		
		if(g.getPlayer1Id() == loggedInPlayer.getId() || g.getPlayer2Id() == loggedInPlayer.getId()) {
			throw new GameException("Invalid action. Player already joined the game.");
		}
		
		g.setPlayer2Id(loggedInPlayer.getId());
		g.setGameState(GameState.INPROGRESS);
		
		try {
			gameRepository.save(g);
		} catch(Exception e) {
			throw e;
		}
		
		return g;
	}
	
	public List<Game> getOpenGames() {
		List<Game> games = gameRepository.findByGameState(GameState.OPEN);
		return games;
	}
	
	public List<Game> getAllGames() {
		return gameRepository.findAll();
	}	
	
	public Game makeMove(String loggedInUser, MoveRequestModel moveRequestModel) throws GameException, Exception{
		Player loggedInPlayer;
		
		try {
			loggedInPlayer = getLoggedInPlayer(loggedInUser);
		} catch (UsernameNotFoundException e) {
			throw e;
		}
		
		Optional<Game> game = gameRepository.findById(moveRequestModel.getGameId());
		game.orElseThrow(() -> new Exception("Game not found " + moveRequestModel.getGameId()));
		
		Game g = game.get();
		
		try {
			validateMove(g, moveRequestModel, loggedInPlayer);
		} catch(GameException e) {
			throw e;
		}
		
		gameLogic.setGame(g);
		moveUtil.setSymbolInPosition(g, moveRequestModel.getPosition(), moveRequestModel.getSymbol());
		gameLogic.updateGameStateAfterMove(moveRequestModel, loggedInPlayer);
		
		try {
			gameRepository.save(g);
		} catch(Exception e) {
			throw e;
		}
		
		return g;
	}
	
	private void validateMove(Game g, MoveRequestModel moveRequestModel, Player loggedInPlayer) throws GameException {
		if(g.getGameState() != GameState.OPEN) {
			throw new GameException("Invalid action. Waiting for another player to join.");
		}
		
		if(g.getGameState() != GameState.INPROGRESS) {
			throw new GameException("Invalid action. Game is either finished or expired.");
		}
		
		if(g.getPlayerTurn() != loggedInPlayer.getId()) {
			throw new GameException("It's not your turn. Please Wait.");
		}
		
		if(g.getPlayer1Id() == loggedInPlayer.getId() && moveRequestModel.getSymbol() != g.getGameData().getPlayer1Symbol()) {
			throw new GameException("Invalid move. Wrong Symbol for this player");
		}
		
		if(g.getPlayer2Id() == loggedInPlayer.getId() && moveRequestModel.getSymbol() != g.getGameData().getPlayer2Symbol()) {
			throw new GameException("Invalid move. Wrong Symbol for this player");
		}
		
		if(moveUtil.getSymbolInPosition(g, moveRequestModel.getPosition()) != null){
			throw new GameException("Invalid move. Position is already filled in.");
		}
	}

}
