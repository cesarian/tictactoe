package nl.cesar.tictactoe.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.domain.GameData;
import nl.cesar.tictactoe.domain.Player;
import nl.cesar.tictactoe.util.GameState;
import nl.cesar.tictactoe.util.MoveUtil;

class GameLogicTest {
	
	private Game game;
	private MoveUtil moveUtil;
	private GameLogic gameLogic;

	@BeforeEach
	void setUp() throws Exception {
		game = new Game(1L);
		moveUtil = new MoveUtil();
		gameLogic = new GameLogic(moveUtil);
	}

	@Test
	void testCheckWinningPatternsPositionNotInRange() {
		assertFalse(gameLogic.checkWinningPatterns(game, 0));
		assertFalse(gameLogic.checkWinningPatterns(game, -1));
		assertFalse(gameLogic.checkWinningPatterns(game, 10));
	}
	
	@Test
	void testCheckWinningPatternsNoFilledInPositions() {
		assertFalse(gameLogic.checkWinningPatterns(game, 1));
		assertFalse(gameLogic.checkWinningPatterns(game, 2));
		assertFalse(gameLogic.checkWinningPatterns(game, 4));
		assertFalse(gameLogic.checkWinningPatterns(game, 8));
	}
	
	@Test
	void testCheckWinningPatternsFalse1() {
		
		GameData gameData = game.getGameData();
		
		gameData.setP1('x');
		gameData.setP2('x');
		gameData.setP3('o');
		
		assertFalse(gameLogic.checkWinningPatterns(game, 3));
	}
	
	@Test
	void testCheckWinningPatternsFalse2() {
		
		GameData gameData = game.getGameData();
		
		gameData.setP1('x');
		gameData.setP4('o');
		gameData.setP7('x');
		
		assertFalse(gameLogic.checkWinningPatterns(game, 4));
	}
	
	@Test
	void testCheckWinningPatternsTrue1() {
		
		GameData gameData = game.getGameData();
		
		gameData.setP1('x');
		gameData.setP2('x');
		gameData.setP3('x');
		
		assertTrue(gameLogic.checkWinningPatterns(game, 2));
	}
	
	@Test
	void testCheckWinningPatternsTrue2() {
		
		GameData gameData = game.getGameData();
		
		gameData.setP1('o');
		gameData.setP4('o');
		gameData.setP7('o');
		
		assertTrue(gameLogic.checkWinningPatterns(game, 7));
	}
	
	@Test
	void testCheckWinningPatternsTrue3() {
		
		GameData gameData = game.getGameData();
		
		gameData.setP3('x');
		gameData.setP5('x');
		gameData.setP7('x');
		
		assertTrue(gameLogic.checkWinningPatterns(game, 5));
	}
	
	@Test
	void testCheckDrawNoFilledInPositions() {
		assertFalse(gameLogic.checkDraw(game));
	}

	@Test
	void testCheckDrawFalse1() {
		GameData gameData = game.getGameData();
		
		gameData.setP1('x');
		gameData.setP2('x');
		gameData.setP3('o');

		assertFalse(gameLogic.checkDraw(game));
	}
	
	@Test
	void testCheckDrawFalse2() {
		GameData gameData = game.getGameData();
		
		gameData.setP1('x');
		gameData.setP2('o');
		gameData.setP3('x');
		gameData.setP4('x');

		assertFalse(gameLogic.checkDraw(game));
	}
	
	@Test
	void testCheckDrawTrue1() {
		GameData gameData = game.getGameData();
		
		gameData.setP1('x');
		gameData.setP2('o');
		gameData.setP3('x');
		gameData.setP4('x');
		gameData.setP5('o');
		gameData.setP6('o');
		gameData.setP7('o');
		gameData.setP8('x');
		gameData.setP9('x');

		assertTrue(gameLogic.checkDraw(game));
	}
	
	@Test
	void testCheckDrawTrue2() {
		GameData gameData = game.getGameData();
		
		gameData.setP1('x');
		gameData.setP2('x');
		gameData.setP3('o');
		gameData.setP4('o');
		gameData.setP5('o');
		gameData.setP6('x');
		gameData.setP7('x');
		gameData.setP8('o');

		assertTrue(gameLogic.checkDraw(game));
	}

	@Test
	void testUpdateGameStateAfterMoveWon() {
		Long playerId = 1L;
		Player player = new Player(playerId);
		
		GameData gameData = game.getGameData();
		
		gameData.setP1('x');
		gameData.setP2('x');
		gameData.setP3('x');
		
		assertTrue(gameLogic.checkWinningPatterns(game, 2));
		
		gameLogic.updateGameStateAfterMove(game, 2, player);
		
		assertEquals(GameState.FINISHED, game.getGameState());
		assertEquals(playerId, game.getWinnerId());
	}
	
	@Test
	void testUpdateGameStateAfterMoveDraw() {
		Long playerId = 1L;
		Player player = new Player(playerId);
		
		GameData gameData = game.getGameData();
		
		gameData.setP1('x');
		gameData.setP2('x');
		gameData.setP3('o');
		gameData.setP4('o');
		gameData.setP5('o');
		gameData.setP6('x');
		gameData.setP7('x');
		gameData.setP8('o');

		assertTrue(gameLogic.checkDraw(game));
		
		gameLogic.updateGameStateAfterMove(game, 7, player);
		
		assertEquals(GameState.DRAW, game.getGameState());
		assertEquals(null, game.getWinnerId());
	}
	
	@Test
	void testUpdateGameStateAfterMoveNextUserTurn() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		Player player = new Player(player1Id);
		game.setPlayer1Id(player1Id);
		game.setPlayer2Id(player2Id);
		game.setGameState(GameState.INPROGRESS);
		
		GameData gameData = game.getGameData();
		game.setPlayerTurn(player1Id);
		
		gameData.setP1('x');
		gameData.setP2('x');
		gameData.setP3('o');
		gameData.setP4('x');
		
		assertFalse(gameLogic.checkWinningPatterns(game, 4));
		assertFalse(gameLogic.checkDraw(game));
		
		gameLogic.updateGameStateAfterMove(game, 4, player);
		
		assertEquals(GameState.INPROGRESS, game.getGameState());
		assertEquals(null, game.getWinnerId());
		assertEquals(player2Id, game.getPlayerTurn());
	}

	@Test
	void testUpdateGameStateAfterPlayerLeaveOpenState() {
		Long player1Id = 1L;
		
		Player player = new Player(player1Id);
		game.setPlayer1Id(player1Id);
		
		gameLogic.updateGameStateAfterPlayerLeave(game, player);
		assertEquals(GameState.EXPIRED, game.getGameState());
	}
	
	@Test
	void testUpdateGameStateAfterPlayer1LeaveInProgressState() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		Player player = new Player(player1Id);
		game.setPlayer1Id(player1Id);
		game.setPlayer2Id(player2Id);
		game.setGameState(GameState.INPROGRESS);
		
		GameData gameData = game.getGameData();
		game.setPlayerTurn(player1Id);
		
		gameData.setP1('x');
		gameData.setP2('x');
		gameData.setP3('o');
		gameData.setP4('x');
		
		assertFalse(gameLogic.checkWinningPatterns(game, 4));
		assertFalse(gameLogic.checkDraw(game));
		
		gameLogic.updateGameStateAfterPlayerLeave(game, player);
		assertEquals(GameState.FINISHED, game.getGameState());
		assertEquals(player2Id, game.getWinnerId());
	}
	
	@Test
	void testUpdateGameStateAfterPlayer2LeaveInProgressState() {
		Long player1Id = 1L;
		Long player2Id = 2L;
		
		Player player = new Player(player2Id);
		game.setPlayer1Id(player1Id);
		game.setPlayer2Id(player2Id);
		game.setGameState(GameState.INPROGRESS);
		
		GameData gameData = game.getGameData();
		game.setPlayerTurn(player1Id);
		
		gameData.setP1('x');
		gameData.setP2('x');
		gameData.setP3('o');
		gameData.setP4('x');
		
		assertFalse(gameLogic.checkWinningPatterns(game, 4));
		assertFalse(gameLogic.checkDraw(game));
		
		gameLogic.updateGameStateAfterPlayerLeave(game, player);
		assertEquals(GameState.FINISHED, game.getGameState());
		assertEquals(player1Id, game.getWinnerId());
	}

}
