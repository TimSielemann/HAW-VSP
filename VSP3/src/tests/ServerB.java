package tests;

import mware_lib.NameService;
import mware_lib.ObjectBroker;

public class ServerB extends Thread {

	private ObjectBroker broker;

	public ServerB(String host, int port, boolean b) {
		ClassTwoAccessorOne two = new ClassTwoAccessorOne();
		broker = new ObjectBroker(host, port, b);
		NameService ns = broker.getNameService();
		ns.rebind(two, "TwoAccessorOne");
	}
	
	public void shutdown(){
		broker.shutDown();
	}

}
