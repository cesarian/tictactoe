package nl.cesar.tictactoe.data.transfer.model;

public class PlayerRegisterResponseDataModel {
	
	private Boolean registerSuccessful;
	private String registerMessage;
	
	public Boolean getRegisterSuccessful() {
		return registerSuccessful;
	}
	
	public String getRegisterMessage() {
		return registerMessage;
	}
	
	public void setRegisterSuccessful(Boolean registerSuccessful) {
		this.registerSuccessful = registerSuccessful;
	}
	
	public void setRegisterMessage(String registerMessage) {
		this.registerMessage = registerMessage;
	}

}
