package nl.cesar.tictactoe.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.domain.GameData;
import nl.cesar.tictactoe.domain.Player;
import nl.cesar.tictactoe.util.GameState;
import nl.cesar.tictactoe.util.MoveUtil;

class GameValidationTest {
	
	private Game game;
	private MoveUtil moveUtil;
	private GameValidation gameValidation;

	@BeforeEach
	void setUp() throws Exception {
		game = new Game(1L);
		moveUtil = new MoveUtil();
		gameValidation = new GameValidation(moveUtil);
	}

	@Test
	void testValidateJoinGamePlayer1AlreadyJoined() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		Player player = new Player(player1Id);
		
		game.setPlayer1Id(player1Id);
		game.setPlayer2Id(player2Id);

		String message = gameValidation.validateJoinGame(game, player);
		
		assertEquals(GameError.ALREADY_JOINED, message);
	}
	
	@Test
	void testValidateJoinGamePlayer2AlreadyJoined() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		Player player = new Player(player2Id);
		
		game.setPlayer1Id(player1Id);
		game.setPlayer2Id(player2Id);

		String message = gameValidation.validateJoinGame(game, player);
		
		assertEquals(GameError.ALREADY_JOINED, message);
	}
	
	@Test
	void testValidateJoinGameIsValid() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		Player player = new Player(player2Id);
		
		game.setPlayer1Id(player1Id);

		String message = gameValidation.validateJoinGame(game, player);
		
		assertNull(message);
	}
	
	@Test
	void testValidateLeaveGameNotPlayerOfGame() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		Long player3Id = 3L;
		
		Player player = new Player(player3Id);
		
		game.setPlayer1Id(player1Id);
		game.setPlayer2Id(player2Id);

		String message = gameValidation.validateLeaveGame(game, player);
		
		assertEquals(GameError.NOT_GAME_PLAYER, message);
	}
	
	@Test
	void testValidateLeaveGameIsValid() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		Player player = new Player(player2Id);
		
		game.setPlayer1Id(player1Id);
		game.setPlayer2Id(player2Id);

		String message = gameValidation.validateLeaveGame(game, player);
		
		assertNull(message);
	}

	@Test
	void testValidateMoveOpenState() {
		Long player1Id = 1L;
		
		Player player = new Player(player1Id);
		Character symbol = 'x';
		int position = 1;
		
		String message = gameValidation.validateMove(game, symbol, position, player);
		assertEquals(message, GameError.WAITING_PLAYER);
	}
	
	@Test
	void testValidateMoveGameFinished() {
		Long player1Id = 1L;
		
		Player player = new Player(player1Id);
		Character symbol = 'x';
		int position = 1;
		
		game.setGameState(GameState.FINISHED);
		
		String message = gameValidation.validateMove(game, symbol, position, player);
		assertEquals(message, GameError.GAME_FINISHED);
	}
	
	@Test
	void testValidateMoveWrongTurn() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		Player player = new Player(player1Id);
		game.setPlayerTurn(player2Id);
		Character symbol = 'x';
		int position = 1;
		
		game.setGameState(GameState.INPROGRESS);
		
		String message = gameValidation.validateMove(game, symbol, position, player);
		assertEquals(message, GameError.INVALID_TURN);
	}
	
	@Test
	void testValidateMoveWrongSymbolPlayer1() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		Player player = new Player(player1Id);
		game.setPlayerTurn(player1Id);
		Character symbol = 'o';
		int position = 1;
		
		game.setPlayer1Id(player1Id);
		game.setPlayer2Id(player2Id);
		
		game.setGameState(GameState.INPROGRESS);
		
		String message = gameValidation.validateMove(game, symbol, position, player);
		assertEquals(message, GameError.WRONG_SYMBOL);
	}
	
	@Test
	void testValidateMoveWrongSymbolPlayer2() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		Player player = new Player(player2Id);
		game.setPlayerTurn(player2Id);
		Character symbol = 'x';
		int position = 1;
		
		game.setPlayer1Id(player1Id);
		game.setPlayer2Id(player2Id);
		
		game.setGameState(GameState.INPROGRESS);
		
		String message = gameValidation.validateMove(game, symbol, position, player);
		assertEquals(message, GameError.WRONG_SYMBOL);
	}
	
	@Test
	void testValidateMovePositionFilled() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		Player player = new Player(player1Id);
		game.setPlayerTurn(player1Id);
		Character symbol = 'x';
		int position = 1;
		
		game.setGameState(GameState.INPROGRESS);
		
		game.setPlayer1Id(player1Id);
		game.setPlayer2Id(player2Id);
		
		GameData gameData = game.getGameData();
		gameData.setP1(symbol);
		
		String message = gameValidation.validateMove(game, symbol, position, player);
		assertEquals(message, GameError.POSITION_FILLED);
	}

}
