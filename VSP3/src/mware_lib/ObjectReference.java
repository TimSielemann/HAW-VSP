package mware_lib;

import java.io.Serializable;
/**
 * Serialisierbares Object zum Referenzieren eines entfernten Objektes.
 * 
 * Wird an den Namensdienst gesendet oder vom Namensdienst erhalten (geschachtelt in einem RAW-Object)
 * 
 * Komponente: MWareLib
 * 
 * @author Tim
 *
 */
public class ObjectReference implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2996363613823653507L;
	private String host;
	private int port;
	private String name;
	
	public ObjectReference(String host, int port, String name) {
		super();
		this.host = host;
		this.port = port;
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ObjectReference [host=" + host + ", port=" + port + ", name="
				+ name + "]";
	}
	
	
}
