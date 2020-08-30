package nl.cesar.tictactoe.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.cesar.tictactoe.domain.Game;
import nl.cesar.tictactoe.domain.Player;
import nl.cesar.tictactoe.util.GameState;
import nl.cesar.tictactoe.util.MoveUtil;

@Component
public class GameValidation {
	
	@Autowired
	private MoveUtil moveUtil;
	
	public GameValidation() {
		// empty constructor
	}
	
	public GameValidation(MoveUtil moveUtil) {
		this.moveUtil = moveUtil;
	}

	public String validateJoinGame(Game game, Player loggedInPlayer) {
		if(game.getPlayer1Id() == loggedInPlayer.getId() || game.getPlayer2Id() == loggedInPlayer.getId()) {
			return GameError.ALREADY_JOINED;
		}
		
		return null;
	}
	
	public String validateLeaveGame(Game game, Player loggedInPlayer) {
		Long playerId = loggedInPlayer.getId();
		
		if(game.getPlayer1Id() != playerId && game.getPlayer2Id() != playerId) {
			return GameError.NOT_GAME_PLAYER;
		}
		
		return null;
	}
	
	public String validateMove(Game game, Character symbol, int position, Player loggedInPlayer){
		
		Long playerId = loggedInPlayer.getId();
		Character moveSymbol = symbol;
		
		if(game.getGameState() == GameState.OPEN) {
			return GameError.WAITING_PLAYER;
		}
		
		if(game.getGameState() != GameState.INPROGRESS) {
			return GameError.GAME_FINISHED;
		}
		
		if(game.getPlayerTurn() != playerId) {
			return GameError.INVALID_TURN;
		}
		
		if(game.getPlayer1Id() == playerId && moveSymbol != game.getGameData().getPlayer1Symbol()) {
			return GameError.WRONG_SYMBOL;
		}
		
		if(game.getPlayer2Id() == playerId && moveSymbol != game.getGameData().getPlayer2Symbol()) {
			return GameError.WRONG_SYMBOL;
		}
		
		if(moveUtil.getSymbolInPosition(game, position) != null){
			return GameError.POSITION_FILLED;
		}
		
		return null;
	}

}
