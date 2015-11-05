package Exceptions;

public class InvalidMoveException extends Exception {
	private String message;
	
	public InvalidMoveException(String message) {
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
