package nl.cesar.tictactoe.data.transfer.model.response;

import nl.cesar.tictactoe.data.transfer.model.ResponseModel;

public class GameJoinResponseModel extends ResponseModel{
	
	private Long gameId;
	private Character player1Symbol;
	private Character player2Symbol;

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	
	public Character getPlayer1Symbol() {
		return player1Symbol;
	}

	public void setPlayer1Symbol(Character player1Symbol) {
		this.player1Symbol = player1Symbol;
	}

	public Character getPlayer2Symbol() {
		return player2Symbol;
	}

	public void setPlayer2Symbol(Character player2Symbol) {
		this.player2Symbol = player2Symbol;
	}

}
