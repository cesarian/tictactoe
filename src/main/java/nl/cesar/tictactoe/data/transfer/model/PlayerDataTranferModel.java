package nl.cesar.tictactoe.data.transfer.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PlayerDataTranferModel {
	@NotNull
	@NotEmpty
	private String username;
	
	@NotNull
	@NotEmpty
	private String password;
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
