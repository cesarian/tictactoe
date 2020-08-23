package nl.cesar.tictactoe.data.transfer.model.response;

import nl.cesar.tictactoe.data.transfer.model.ResponseModel;

public class RegisterResponseModel extends ResponseModel{
	
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
