package Exceptions;

public class WrongTurnException extends Exception{
	private String message;
	
	public WrongTurnException(String message) {
		super(message);
		this.message = message;
	}
	@Override
	public String toString() {
		return message;
	}
	@Override
	public String getMessage() {
		return message;
	}
}

