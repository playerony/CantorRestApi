package pl.playerony.cantor.exceptions;

public class CantorRestApiException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CantorRestApiException() {
		super();
	}
	
	public CantorRestApiException(String message) {
		super(message);
	}
	
	public CantorRestApiException(String message, Throwable e) {
		super(message, e);
	}
	
}
