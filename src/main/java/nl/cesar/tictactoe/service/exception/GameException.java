package nl.cesar.tictactoe.service.exception;

public class GameException extends Exception {
	
	public GameException(String message) {
		super(message);
	}
	
	public GameException(String message, Throwable t) {
		super(message, t);
	}

}
