package nl.cesar.tictactoe.data.transfer.model;

public class ResponseModel {
	private String message;
	private Boolean actionSuccessful;
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public Boolean getActionSuccessful() {
		return actionSuccessful;
	}

	public void setActionSuccessful(Boolean actionSuccessful) {
		this.actionSuccessful = actionSuccessful;
	}

}
