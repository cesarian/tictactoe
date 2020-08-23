package nl.cesar.tictactoe.data.transfer.model.response;

import nl.cesar.tictactoe.data.transfer.model.ResponseModel;

public class LoginResponseModel extends ResponseModel {
	private String username;
	private Long userId;

	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
