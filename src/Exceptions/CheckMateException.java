package Exceptions;

public class CheckMateException extends Exception{
	private String message;
	
	public CheckMateException(String message) {
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
