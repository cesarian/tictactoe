package nl.cesar.tictactoe.data.transfer.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GameJoinRequestModel {
	
	@NotNull
	@NotEmpty
	private Long gameId;
	
	@NotNull
	@NotEmpty
	private Long playerId;

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	
}
