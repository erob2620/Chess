package Exceptions;

public class CheckException extends Exception{
	private String message;
	
	public CheckException(String message) {
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
