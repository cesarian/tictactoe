package nl.cesar.tictactoe.data.transfer.model.request;

public class MoveRequestModel {
	private Character symbol;
	private Integer position;
	private Long gameId;
	
	public Character getSymbol() {
		return symbol;
	}
	
	public void setSymbol(Character symbol) {
		this.symbol = symbol;
	}
	
	public Integer getPosition() {
		return position;
	}
	
	public void setPosition(Integer position) {
		this.position = position;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	
}
