package nameservice;

import java.io.Serializable;

public class ObjectReference implements Serializable {
	
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
	
	
}
