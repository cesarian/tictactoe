package nl.cesar.tictactoe.data.transfer.model.response;

import nl.cesar.tictactoe.data.transfer.model.ResponseModel;

public class MoveResponseModel extends ResponseModel {
	private Boolean winningMove;

	public Boolean getWinningMove() {
		return winningMove;
	}

	public void setWinningMove(Boolean winningMove) {
		this.winningMove = winningMove;
	}
}
