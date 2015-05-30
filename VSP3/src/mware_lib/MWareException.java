package mware_lib;


public class MWareException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7123021356538696876L;
	
	public MWareException(String message, Exception e) {
		super(message, e);
	}

	public MWareException(String string) {
		super(string);
	}

	

}
