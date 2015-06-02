package mware_lib;

/**
 * Exception für Fehler innerhalb der Middleware
 * (siehe Entwurf)
 * @author Tim
 *
 */
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
