package Exceptions;

public class PieceBlockedException extends RuntimeException {
	private String message;
	
	public PieceBlockedException(String message) {
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
