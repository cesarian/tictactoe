package nl.cesar.tictactoe.data.transfer.model.response;

import nl.cesar.tictactoe.data.transfer.model.ResponseModel;
import nl.cesar.tictactoe.util.GameState;

public class MoveResponseModel extends ResponseModel {
	private GameState gameState;
	private Long winnerId;

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Long getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(Long winnerId) {
		this.winnerId = winnerId;
	}
}
