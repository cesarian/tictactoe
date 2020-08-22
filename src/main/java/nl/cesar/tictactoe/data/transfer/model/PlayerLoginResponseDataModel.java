package nl.cesar.tictactoe.data.transfer.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PlayerLoginResponseDataModel {
	@NotNull
	@NotEmpty
	private String username;
	
	@NotNull
	@NotEmpty
	private Integer userId;
	
	public PlayerLoginResponseDataModel(String username, Integer userId) {
		this.username = username;
		this.userId = userId;
	}

	public String getUsername() {
		return this.username;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	

}
